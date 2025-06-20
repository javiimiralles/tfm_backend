package com.backend.backend.services.dashboard;

import java.util.List;

import com.backend.backend.dto.DashboardIcomesExpensesDTO;
import com.backend.backend.dto.DashboardSummaryDTO;

public interface DashboardService {
    DashboardSummaryDTO getDashboardSummary(Long idEmpresa);

    DashboardIcomesExpensesDTO getDashboardIcomesExpenses(Long idEmpresa);

    List<Integer> getVentasUltimos6Meses(Long idEmpresa);
}
