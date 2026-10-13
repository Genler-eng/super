package com.supermercado.mercado.dto;

import lombok.Data;

@Data 
public class DetalleVentaResponseDTO {
    private Long id;
    private Long productoId;
    private String nombreProducto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}
