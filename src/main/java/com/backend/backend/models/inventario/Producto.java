package com.backend.backend.models.inventario;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria", referencedColumnName = "id")
    private CategoriaProducto categoria;

    @Column(name = "id_empresa", nullable = false)
    private Long idEmpresa;

    @Column(name = "nombre", length = 150, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    @Column(name = "imagen_url")
    private String imagenUrl;

    @Column(name = "precio_venta", nullable = false)
    private BigDecimal precioVenta;

    @Column(name = "impuesto_venta", nullable = false)
    private BigDecimal impuestoVenta;

    @Column(name = "coste", nullable = false)
    private BigDecimal coste;

    @Column(name = "impuesto_compra", nullable = false)
    private BigDecimal impuestoCompra;

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
