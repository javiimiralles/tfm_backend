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
    @Column(name = "id")
    private Long id;

    @Column(name = "id_empresa", nullable = false)
    private Long idEmpresa;

    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "apellidos", length = 150)
    private String apellidos;

    @Column(name = "nif", length = 9, unique = true)
    private String nif;

    @Column(name = "email", length = 150, unique = true, nullable = false)
    private String email;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "direccion")
    private String direccion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pais", referencedColumnName = "id")
    private Pais pais;

    @Column(name = "provincia", length = 100)
    private String provincia;

    @Column(name = "poblacion", length = 100)
    private String poblacion;

    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;

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
