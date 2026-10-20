package com.supermercado.mercado.service;

import com.supermercado.mercado.entity.Proveedor;
import com.supermercado.mercado.exception.ResourceNotFoundException;
import com.supermercado.mercado.repository.ProveedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public List<Proveedor> obtenerTodos() {
        return proveedorRepository.findAll();
    }

    public Proveedor obtenerPorId(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + id));
    }

    public List<Proveedor> buscarPorNombreOContacto(String criterio) {
        return proveedorRepository.findByNombreContainingIgnoreCaseOrTelefonoContainingIgnoreCase(criterio, criterio);
    }

  @Transactional
public Proveedor guardar(Proveedor proveedor) {
    if (proveedor.getNombre() == null || proveedor.getNombre().isBlank()) {
        throw new IllegalArgumentException("El nombre del proveedor no puede estar vacío.");
    }
    return proveedorRepository.save(proveedor);
}

    @Transactional
    public Proveedor actualizar(Long id, Proveedor proveedorDetalles) {
        Proveedor proveedorExistente = obtenerPorId(id);

        if (proveedorDetalles.getNombre() == null || proveedorDetalles.getNombre().isBlank()) {
            // CORREGIDO: Se quitó el "s:" que sobraba
            throw new IllegalArgumentException("El nombre del proveedor no puede estar vacío.");
        }

        proveedorExistente.setNombre(proveedorDetalles.getNombre());
        proveedorExistente.setTelefono(proveedorDetalles.getTelefono());
        proveedorExistente.setCorreo(proveedorDetalles.getCorreo());
  

        return proveedorRepository.save(proveedorExistente);
    }

    @Transactional
    public void eliminar(Long id) {
        Proveedor proveedor = obtenerPorId(id);
        proveedorRepository.delete(proveedor);
    }
}