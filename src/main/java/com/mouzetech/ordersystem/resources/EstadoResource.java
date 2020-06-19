package com.mouzetech.ordersystem.resources;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mouzetech.ordersystem.domain.Cidade;
import com.mouzetech.ordersystem.domain.Estado;
import com.mouzetech.ordersystem.dto.CidadeDTO;
import com.mouzetech.ordersystem.dto.EstadoDTO;
import com.mouzetech.ordersystem.services.CidadeService;
import com.mouzetech.ordersystem.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {

	@Autowired
	private EstadoService service;
	
	@Autowired
	private CidadeService cidadeService;
	
	@GetMapping
	public ResponseEntity<List<EstadoDTO>> findAll(){
		List<Estado> list = service.buscarTodos();
		List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@GetMapping(value="{estadoId}/cidades")
	public ResponseEntity<List<CidadeDTO>> findCidadesByEstado(@PathVariable Integer estadoId){
		List<Cidade> list = cidadeService.buscarPorEstado(estadoId);
		List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}	
}
