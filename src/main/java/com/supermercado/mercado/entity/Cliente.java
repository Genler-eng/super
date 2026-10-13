package com.supermercado.mercado.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity 
@Table(name ="cliente")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Cliente {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false, length = 20)
    private String nombre;

    @Column(nullable = false,unique = true, length = 20)
    private String documento;

    @Column(length = 20)
    private String telefono;
}
