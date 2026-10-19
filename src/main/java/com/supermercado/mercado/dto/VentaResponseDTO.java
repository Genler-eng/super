package com.supermercado.mercado.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class VentaResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private Long clienteId;
    private String clienteNombre;
    private Double total;
    private List<DetalleVentaResponseDTO> detalles;

    public VentaResponseDTO(Long id, LocalDateTime fecha, Long clienteId, String clienteNombre, Double total, List<DetalleVentaResponseDTO> detalles) {
        this.id = id;
        this.fecha = fecha;
        this.clienteId = clienteId;
        this.clienteNombre = clienteNombre;
        this.total = total;
        this.detalles = detalles;
    }
}