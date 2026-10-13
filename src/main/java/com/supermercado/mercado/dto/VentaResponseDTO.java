package com.supermercado.mercado.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data 
public class VentaResponseDTO {
    private Long id;
    private LocalDateTime fecha;
    private Double total;
    private Long clienteId;
    private String nombreCliente;
    private List<DetalleVentaResponseDTO> detalles;


}