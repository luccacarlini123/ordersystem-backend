package com.mouzetech.ordersystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mouzetech.ordersystem.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
