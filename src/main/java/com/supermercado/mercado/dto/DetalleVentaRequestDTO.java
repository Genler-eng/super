package com.supermercado.mercado.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data 
public class DetalleVentaRequestDTO {

    @NotNull(message = "El id del producto es obligatorio")
    private Long productoId;


    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 1")
    private Integer cantidad;
    
}
