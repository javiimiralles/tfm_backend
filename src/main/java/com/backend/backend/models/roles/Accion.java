package com.backend.backend.models.roles;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "acciones")
public class Accion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    @Column(name = "accion", length = 50, nullable = false)
    private String accion;
}
