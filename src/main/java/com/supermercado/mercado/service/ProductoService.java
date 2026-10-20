package com.supermercado.mercado.service;

import com.supermercado.mercado.entity.Categoria;
import com.supermercado.mercado.entity.Producto;
import com.supermercado.mercado.entity.Proveedor;
import com.supermercado.mercado.exception.ResourceNotFoundException;
import com.supermercado.mercado.repository.CategoriaRepository;
import com.supermercado.mercado.repository.ProductoRepository;
import com.supermercado.mercado.repository.ProveedorRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
  private final ProveedorRepository proveedorRepository;
  
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> obtenerProductosConStockBajo(int limiteStock) {
        return productoRepository.findByStockLessThanEqual(limiteStock);
    }

    @Transactional
    public Producto guardar(Producto producto) {
        validarRelacionesImperativa(producto);
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto actualizar(Long id, Producto productoDetalles) {
        Producto productoExistente = obtenerPorId(id);
        validarRelacionesImperativa(productoDetalles);

        productoExistente.setNombre(productoDetalles.getNombre());
        productoExistente.setPrecio(productoDetalles.getPrecio());
        productoExistente.setStock(productoDetalles.getStock());
        productoExistente.setCategoria(productoDetalles.getCategoria());
        productoExistente.setProveedor(productoDetalles.getProveedor());

        return productoRepository.save(productoExistente);
    }

    @Transactional
    public void eliminar(Long id) {
        Producto producto = obtenerPorId(id);
        productoRepository.delete(producto);
    }

    private void validarRelacionesImperativa(Producto producto) {
        if (producto.getCategoria() != null && producto.getCategoria().getId() != null) {
            Categoria categoria = categoriaRepository.findById(producto.getCategoria().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + producto.getCategoria().getId()));
            producto.setCategoria(categoria);
        } else {
            throw new IllegalArgumentException("El producto debe estar asociado a una categoría válida.");
        }

        if (producto.getProveedor() != null && producto.getProveedor().getId() != null) {
            Proveedor proveedor = proveedorRepository.findById(producto.getProveedor().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + producto.getProveedor().getId()));
            producto.setProveedor(proveedor);
        }
    }
}