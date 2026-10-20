package com.supermercado.mercado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class DetalleVentaResponseDTO {
    private Long id;
    private Long productoId;
    private String nombreProducto;
    private Integer cantidad;
    private Double precioUnitario;
    private Double subtotal;
}
