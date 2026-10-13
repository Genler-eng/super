package com.supermercado.mercado.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermercado.mercado.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByDocumento(String documento);

}