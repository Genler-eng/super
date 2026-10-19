package com.supermercado.mercado.service;

import com.supermercado.mercado.dto.DetalleVentaRequestDTO;
import com.supermercado.mercado.dto.DetalleVentaResponseDTO;
import com.supermercado.mercado.dto.VentaRequestDTO;
import com.supermercado.mercado.dto.VentaResponseDTO;
import com.supermercado.mercado.entity.Cliente;
import com.supermercado.mercado.entity.DetalleVenta;
import com.supermercado.mercado.entity.Producto;
import com.supermercado.mercado.entity.Venta;
import com.supermercado.mercado.exception.ResourceNotFoundException;
import com.supermercado.mercado.repository.ClienteRepository;
import com.supermercado.mercado.repository.ProductoRepository;
import com.supermercado.mercado.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final ProductoRepository productoRepository;

    public List<VentaResponseDTO> obtenerTodas() {
        return ventaRepository.findAll().stream()
                .map(this::mapearAVentaResponseDTO)
                .toList();
    }

    public VentaResponseDTO obtenerPorId(Long id) {
        return ventaRepository.findById(id)
                .map(this::mapearAVentaResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + id));
    }

    @Transactional
    public VentaResponseDTO registrarVenta(VentaRequestDTO request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + request.getClienteId()));

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(LocalDateTime.now());

        List<DetalleVenta> detalles = Optional.ofNullable(request.getDetalles())
                .orElseGet(List::of)
                .stream()
                .map(detalleDTO -> crearDetalleYDescontarStock(venta, detalleDTO))
                .toList();

        venta.setDetalles(detalles);

        double totalVenta = detalles.stream()
                .mapToDouble(DetalleVenta::getSubtotal)
                .sum();

        venta.setTotal(totalVenta);

        Venta ventaGuardada = ventaRepository.save(venta);
        return mapearAVentaResponseDTO(ventaGuardada);
    }

    private DetalleVenta crearDetalleYDescontarStock(Venta venta, DetalleVentaRequestDTO detalleDTO) {
        Producto producto = productoRepository.findById(detalleDTO.getProductoId())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + detalleDTO.getProductoId()));

        if (producto.getStock() < detalleDTO.getCantidad()) {
            throw new IllegalArgumentException("Stock insuficiente para " + producto.getNombre() + 
                    ". Disponible: " + producto.getStock());
        }

        producto.setStock(producto.getStock() - detalleDTO.getCantidad());
        productoRepository.save(producto);

        DetalleVenta detalle = new DetalleVenta();
        detalle.setVenta(venta);
        detalle.setProducto(producto);
        detalle.setCantidad(detalleDTO.getCantidad());
        detalle.setPrecioUnitario(producto.getPrecio());
        detalle.setSubtotal(producto.getPrecio() * detalleDTO.getCantidad());

        return detalle;
    }

    private VentaResponseDTO mapearAVentaResponseDTO(Venta venta) {
        List<DetalleVentaResponseDTO> detallesDTO = venta.getDetalles().stream()
                .map(detalle -> new DetalleVentaResponseDTO(
                        detalle.getId(),
                        detalle.getProducto().getId(),
                        detalle.getProducto().getNombre(),
                        detalle.getCantidad(),
                        detalle.getPrecioUnitario(),
                        detalle.getSubtotal()
                ))
                .toList();

        return new VentaResponseDTO(
                venta.getId(),
                venta.getFecha(),
                venta.getCliente().getId(),
                venta.getCliente().getNombre(),
                venta.getTotal(),
                detallesDTO
        );
    }
}