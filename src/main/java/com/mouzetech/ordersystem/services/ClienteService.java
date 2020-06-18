package com.mouzetech.ordersystem.services;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mouzetech.ordersystem.domain.Cidade;
import com.mouzetech.ordersystem.domain.Cliente;
import com.mouzetech.ordersystem.domain.Endereco;
import com.mouzetech.ordersystem.domain.enums.Perfil;
import com.mouzetech.ordersystem.domain.enums.TipoCliente;
import com.mouzetech.ordersystem.dto.ClienteDTO;
import com.mouzetech.ordersystem.dto.ClienteNewDTO;
import com.mouzetech.ordersystem.repositories.ClienteRepository;
import com.mouzetech.ordersystem.repositories.EnderecoRepository;
import com.mouzetech.ordersystem.security.UserSS;
import com.mouzetech.ordersystem.services.exceptions.AuthorizationException;
import com.mouzetech.ordersystem.services.exceptions.DataIntegrityException;
import com.mouzetech.ordersystem.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepo;
	
	@Autowired
	private BCryptPasswordEncoder pe;
	
	@Autowired
	private S3Service s3Service;
	
	public Cliente buscarPorId(Integer id) {
		UserSS user = UserService.authenticated();
		if(user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Id não encontrado: " + id + ". Tipo: " + Cliente.class.getName()));
	}

	public List<Cliente> buscarTodos() {
		return repo.findAll();
	}
	
	@Transactional
	public Cliente inserir(Cliente obj) {
		obj.setId(null);
		repo.save(obj); 
		enderecoRepo.saveAll(obj.getEnderecos());
		return obj;
	}

	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}

	public Cliente editar(Cliente obj) {
		Cliente newObj = buscarPorId(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void excluir(Integer id) {
		buscarPorId(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Há pedidos associados, impossível excluir.");
		}
	}

	public Cliente fromDTO(ClienteDTO dto) {
		return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null, null);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cidade = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cidade, cli);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if(objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}	
		if(objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;		
	}
	
	public URI uploadFile(MultipartFile mf) {
		UserSS user = UserService.authenticated();
		URI uri = s3Service.uploadFile(mf);
		Cliente obj = buscarPorId(user.getId());
		obj.setImageUrl(uri.toString());
		repo.save(obj);
		return uri;
	}
}
