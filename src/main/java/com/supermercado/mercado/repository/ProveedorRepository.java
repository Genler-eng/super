package com.supermercado.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.supermercado.mercado.entity.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
}