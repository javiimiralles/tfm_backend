package com.backend.backend.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductoDTO {

    private Long id;

    private String categoria;

    private Long idEmpresa;

    private String nombre;

    private String imagenUrl;

    private BigDecimal precioVenta;

    private Integer stock;

    private Date fechaBaja;
}
