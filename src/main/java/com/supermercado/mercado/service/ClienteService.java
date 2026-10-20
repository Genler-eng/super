package com.supermercado.mercado.service;

import com.supermercado.mercado.entity.Cliente;
import com.supermercado.mercado.exception.ResourceNotFoundException;
import com.supermercado.mercado.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Transactional
    public Cliente guardar(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio.");
        }
        if (cliente.getDocumento() == null || cliente.getDocumento().isBlank()) {
            throw new IllegalArgumentException("El documento del cliente es obligatorio.");
        }
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente actualizar(Long id, Cliente clienteDetalles) {
        Cliente clienteExistente = obtenerPorId(id);

        if (clienteDetalles.getNombre() == null || clienteDetalles.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio.");
        }
        if (clienteDetalles.getDocumento() == null || clienteDetalles.getDocumento().isBlank()) {
            throw new IllegalArgumentException("El documento del cliente es obligatorio.");
        }

        clienteExistente.setNombre(clienteDetalles.getNombre());
        clienteExistente.setDocumento(clienteDetalles.getDocumento());
        clienteExistente.setTelefono(clienteDetalles.getTelefono());

        return clienteRepository.save(clienteExistente);
    }

    @Transactional
    public void eliminar(Long id) {
        Cliente cliente = obtenerPorId(id);
        clienteRepository.delete(cliente);
    }
}