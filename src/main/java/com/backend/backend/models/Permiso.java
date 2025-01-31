package com.backend.backend.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "permisos")
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_rol", nullable = false)
    private Long idRol;

    @Column(name = "id_accion", nullable = false)
    private Long idAccion;
}
