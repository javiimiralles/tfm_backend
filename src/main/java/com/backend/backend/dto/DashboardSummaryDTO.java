package com.backend.backend.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DashboardSummaryDTO {

    private BigDecimal totalFacturado;

    private BigDecimal gastosPedidosProveedores;

    private Integer ventasEnCurso;

    private Integer clientesNuevos;

    private Integer pedidosProveedores;
}
