package com.mouzetech.ordersystem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouzetech.ordersystem.domain.Categoria;
import com.mouzetech.ordersystem.repositories.CategoriaRepository;
import com.mouzetech.ordersystem.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria buscarPorId(Integer id){
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+id+". Tipo: "+Categoria.class.getName()));
	}
	
}
