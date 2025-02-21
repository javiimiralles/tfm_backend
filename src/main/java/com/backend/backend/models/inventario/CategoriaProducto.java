package com.backend.backend.models.inventario;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "categorias_productos")
public class CategoriaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    @Column(name = "id_empresa", nullable = false)
    private Long idEmpresa;
}
