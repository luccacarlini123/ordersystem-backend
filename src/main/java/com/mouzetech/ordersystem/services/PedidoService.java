package com.mouzetech.ordersystem.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouzetech.ordersystem.domain.Pedido;
import com.mouzetech.ordersystem.repositories.PedidoRepository;
import com.mouzetech.ordersystem.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	public Pedido buscarPorId(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado. Id: "+id+" Tipo: "+Pedido.class.getName()));
	}
	
}
