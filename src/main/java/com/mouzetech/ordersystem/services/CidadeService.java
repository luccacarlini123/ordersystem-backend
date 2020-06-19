package com.mouzetech.ordersystem.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mouzetech.ordersystem.domain.Cidade;
import com.mouzetech.ordersystem.domain.Estado;
import com.mouzetech.ordersystem.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repo;

	@Autowired
	private EstadoService estadoService;

	public List<Cidade> buscarPorEstado(Integer id) {
		Estado estado = estadoService.buscarPorId(id);
		List<Cidade> list = repo.findByEstado(estado);
		return list;
	}
}
