package com.supermercado.mercado.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.supermercado.mercado.entity.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    List<Proveedor> findByNombreContainingIgnoreCaseOrContactoContainingIgnoreCase(String criterio, String criterio2);
}