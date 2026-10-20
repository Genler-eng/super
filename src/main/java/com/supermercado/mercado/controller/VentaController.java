package com.supermercado.mercado.controller;

import com.supermercado.mercado.dto.VentaRequestDTO;
import com.supermercado.mercado.dto.VentaResponseDTO;
import com.supermercado.mercado.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(ventaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<VentaResponseDTO> registrarVenta(@Valid @RequestBody VentaRequestDTO ventaRequestDTO) {
        VentaResponseDTO nuevaVenta = ventaService.registrarVenta(ventaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
    }
}