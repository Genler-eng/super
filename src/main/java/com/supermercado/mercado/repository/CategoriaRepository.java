package com.supermercado.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermercado.mercado.entity.Categoria;

public interface CategoriaRepository  extends JpaRepository<Categoria, Long>{
    
}
