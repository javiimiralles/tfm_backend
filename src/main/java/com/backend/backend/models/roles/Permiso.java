package com.backend.backend.models.roles;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "permisos")
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", referencedColumnName = "id", nullable = false)
    @JsonBackReference
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "id_accion", referencedColumnName = "id", nullable = false)
    private Accion accion;
}
