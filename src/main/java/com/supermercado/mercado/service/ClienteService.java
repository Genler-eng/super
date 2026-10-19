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
        if (cliente.getEmail() == null || cliente.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email del cliente es obligatorio.");
        }
        return clienteRepository.save(cliente);
    }

    @Transactional
    public Cliente actualizar(Long id, Cliente clienteDetalles) {
        Cliente clienteExistente = obtenerPorId(id);

        if (clienteDetalles.getEmail() == null || clienteDetalles.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email del cliente es obligatorio.");
        }

        clienteExistente.setNombre(clienteDetalles.getNombre());
        clienteExistente.setApellido(clienteDetalles.getApellido());
        clienteExistente.setEmail(clienteDetalles.getEmail());
        clienteExistente.setTelefono(clienteDetalles.getTelefono());
        clienteExistente.setDireccion(clienteDetalles.getDireccion());

        return clienteRepository.save(clienteExistente);
    }

    @Transactional
    public void eliminar(Long id) {
        Cliente cliente = obtenerPorId(id);
        clienteRepository.delete(cliente);
    }
}
