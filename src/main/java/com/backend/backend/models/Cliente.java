package com.backend.backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_empresa", nullable = false)
    private Long idEmpresa;

    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "apellidos", length = 150)
    private String apellidos;

    @Column(name = "email", length = 150, unique = true, nullable = false)
    private String email;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "id_resp_alta", nullable = false)
    private Long idRespAlta;

    @Column(name = "fecha_alta", nullable = false)
    private Date fechaAlta;

    @Column(name = "id_resp_modif")
    private Long idRespModif;

    @Column(name = "fecha_modif")
    private Date fechaModif;

    @Column(name = "id_resp_baja")
    private Long idRespBaja;

    @Column(name = "fecha_baja")
    private Date fechaBaja;
}
