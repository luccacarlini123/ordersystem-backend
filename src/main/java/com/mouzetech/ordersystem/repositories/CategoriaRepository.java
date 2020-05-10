package com.mouzetech.ordersystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mouzetech.ordersystem.domain.Categoria;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> { 
}
