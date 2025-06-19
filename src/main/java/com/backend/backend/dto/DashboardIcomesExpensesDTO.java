package com.backend.backend.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class DashboardIcomesExpensesDTO {
    
    private BigDecimal totalIngresos;

    private BigDecimal totalGastos;

    private Double ratioBeneficio;

    List<BigDecimal> ingresosUltimos6Meses;

    List<BigDecimal> gastosUltimos6Meses;
}
