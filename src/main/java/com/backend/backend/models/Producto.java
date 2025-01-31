package com.backend.backend.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_categoria", nullable = false)
    private Long idCategoria;

    @Column(name = "id_empresa", nullable = false)
    private Long idEmpresa;

    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "precio_venta", nullable = false)
    private Double precioVenta;

    @Column(name = "impuesto_venta", nullable = false)
    private Double impuestoVenta;

    @Column(name = "coste", nullable = false)
    private Double coste;

    @Column(name = "impuesto_compra", nullable = false)
    private Double impuestoCompra;

    @Column(name = "stock", nullable = false)
    private Integer stock;

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
