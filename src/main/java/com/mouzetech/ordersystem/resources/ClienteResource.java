package com.mouzetech.ordersystem.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mouzetech.ordersystem.domain.Cliente;
import com.mouzetech.ordersystem.dto.ClienteDTO;
import com.mouzetech.ordersystem.dto.ClienteNewDTO;
import com.mouzetech.ordersystem.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findById(@PathVariable Integer id){
		Cliente obj = service.buscarPorId(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll(){
		List<Cliente> list = service.buscarTodos();
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value="/page")
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value="page", defaultValue="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage,
			@RequestParam(value="direction", defaultValue="ASC") String direction,
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy){
		
		Page<Cliente> pageList = service.findPage(page, linesPerPage, direction, orderBy);
		Page<ClienteDTO> pageListDto = pageList.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(pageListDto);
	}
	
	@PostMapping
	public ResponseEntity<Cliente> insert(@Valid @RequestBody ClienteNewDTO objDto){
		Cliente obj = service.fromDTO(objDto);
		service.inserir(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Cliente> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDto){
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		service.editar(obj);
		return ResponseEntity.noContent().build();
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.excluir(id);
		return ResponseEntity.noContent().build();
	}
}
