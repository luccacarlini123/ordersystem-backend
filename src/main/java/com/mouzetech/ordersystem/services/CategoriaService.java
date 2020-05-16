package com.mouzetech.ordersystem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mouzetech.ordersystem.domain.Categoria;
import com.mouzetech.ordersystem.repositories.CategoriaRepository;
import com.mouzetech.ordersystem.services.exceptions.DataIntegrityException;
import com.mouzetech.ordersystem.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscarPorId(Integer id){
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+". Tipo: "+Categoria.class.getName()));
	}
	
	public Categoria inserir(Categoria obj) {
		return repo.save(obj);	
	}
	
	public Categoria editar(Categoria obj) {
		buscarPorId(obj.getId());
		return repo.save(obj);
	}
	
	public void excluir(Integer id) {
		try {
		repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Este objeto possui itens associados a ele, impossível excluir.");
		}
	}
}
