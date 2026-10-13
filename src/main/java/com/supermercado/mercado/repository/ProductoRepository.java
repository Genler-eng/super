package com.supermercado.mercado.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supermercado.mercado.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByCategoriaId(Long categoriaId);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByStockLessThanEqual(Integer limite);
    
}
