package com.backend.backend.services.dashboard;

import com.backend.backend.dto.DashboardSummaryDTO;

public interface DashboardService {
    DashboardSummaryDTO getDashboardSummary(Long idEmpresa);
}
