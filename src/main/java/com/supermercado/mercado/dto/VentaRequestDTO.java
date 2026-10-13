package com.supermercado.mercado.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class VentaRequestDTO {
    
    @NotNull(message = "El id del cliente es obligatorio")
    private Long clienteId;

    @NotEmpty(message = "La venta no puede registrarse sin detalles")
    @Valid
    private List<DetalleVentaRequestDTO> detalles;


}
