package com.backend.backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "razon_social", length = 50, nullable = false)
    private String razonSocial;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "email", length = 150, nullable = false)
    private String email;

    @Column(name = "fecha_alta", nullable = false)
    private Date fechaAlta;

    @Column(name = "fecha_baja")
    private Date fechaBaja;
}
