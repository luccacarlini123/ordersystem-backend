package com.mouzetech.ordersystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mouzetech.ordersystem.domain.Cidade;
import com.mouzetech.ordersystem.domain.Estado;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

	@Transactional(readOnly=true)
	public List<Cidade> findByEstado(Estado estado);
	
	
}
