package com.supermercado.mercado.service;

import com.supermercado.mercado.entity.Categoria;
import com.supermercado.mercado.exception.ResourceNotFoundException;
import com.supermercado.mercado.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<Categoria> obtenerTodas() {
        return categoriaRepository.findAll();
    }

    public Categoria obtenerPorId(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + id));
    }

    public List<String> obtenerNombresCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(Categoria::getNombre)
                .toList();
    }

    @Transactional
    public Categoria guardar(Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio.");
        }
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public Categoria actualizar(Long id, Categoria categoriaDetalles) {
        Categoria categoriaExistente = obtenerPorId(id);

        if (categoriaDetalles.getNombre() == null || categoriaDetalles.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre de la categoría es obligatorio.");
        }

        categoriaExistente.setNombre(categoriaDetalles.getNombre());
        categoriaExistente.setDescripcion(categoriaDetalles.getDescripcion());

        return categoriaRepository.save(categoriaExistente);
    }

    @Transactional
    public void eliminar(Long id) {
        Categoria categoria = obtenerPorId(id);
        categoriaRepository.delete(categoria);
    }
}