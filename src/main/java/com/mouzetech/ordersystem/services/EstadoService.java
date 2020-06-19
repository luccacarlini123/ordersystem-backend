package com.mouzetech.ordersystem.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouzetech.ordersystem.domain.Estado;
import com.mouzetech.ordersystem.repositories.EstadoRepository;
import com.mouzetech.ordersystem.services.exceptions.ObjectNotFoundException;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;
	
	public List<Estado> buscarTodos(){
		List<Estado> list = repo.findAllByOrderByNome();
		return list;
	}
	
	public Estado buscarPorId(Integer id) {
		Optional<Estado> obj = repo.findById(id);
		return obj.orElseThrow(() -> 
		new ObjectNotFoundException("Objeto não encontrado! Id: "+id+". Tipo: "+Estado.class.getName()));
	}
	
}
