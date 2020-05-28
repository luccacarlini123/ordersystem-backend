package com.mouzetech.ordersystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.mouzetech.ordersystem.domain.Categoria;
import com.mouzetech.ordersystem.domain.Produto;
import com.mouzetech.ordersystem.repositories.CategoriaRepository;
import com.mouzetech.ordersystem.repositories.ProdutoRepository;
import com.mouzetech.ordersystem.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepo;
	
	public List<Produto> buscarTodos(){
		return repo.findAll();
	}
	
	public Produto buscarPorId(Integer id) {
		Optional<Produto> produto = repo.findById(id);
		return produto.orElseThrow(() -> new ObjectNotFoundException("Id não encontrado: "+id+", Tipo: "+Produto.class.getName()));
	}
	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepo.findAllById(ids);
		return repo.search(nome, categorias, pageRequest);	
	}
	
}
