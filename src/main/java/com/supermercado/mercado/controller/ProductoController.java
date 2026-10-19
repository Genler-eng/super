package com.supermercado.mercado.controller;

import com.supermercado.mercado.entity.Producto;
import com.supermercado.mercado.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    // 1. GET - Obtener todos los productos
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodos() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    // 2. GET con @PathVariable - Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    // 3. GET con @RequestParam (requerido) - Buscar productos por coincidencia en el nombre
    // Ejemplo URL: /api/productos/buscar?nombre=leche
    @GetMapping("/buscar")
    public ResponseEntity<List<Producto>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.buscarPorNombre(nombre));
    }

    // 4. GET con @RequestParam (con valor por defecto) - Filtrar por stock bajo
    // Ejemplo URL: /api/productos/stock-bajo?limite=10
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Producto>> obtenerStockBajo(
            @RequestParam(defaultValue = "5") int limite) {
        return ResponseEntity.ok(productoService.obtenerProductosConStockBajo(limite));
    }

    // 5. POST con @Valid y @RequestBody - Crear un nuevo producto con validación
    @PostMapping
    public ResponseEntity<Producto> guardar(@Valid @RequestBody Producto producto) {
        Producto nuevoProducto = productoService.guardar(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }

    // 6. PUT con @PathVariable, @Valid y @RequestBody - Actualizar un producto existente
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(
            @PathVariable Long id, 
            @Valid @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizar(id, producto);
        return ResponseEntity.ok(productoActualizado);
    }

    // 7. DELETE con @PathVariable - Eliminar un producto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}