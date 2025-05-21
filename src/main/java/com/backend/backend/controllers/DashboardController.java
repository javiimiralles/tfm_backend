package com.backend.backend.controllers;

import com.backend.backend.dto.HttpResponse;
import com.backend.backend.services.dashboard.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary/{idEmpresa}")
    public ResponseEntity<Object> getDashboardSummary(@PathVariable Long idEmpresa) {
        try {
            return ResponseEntity.ok(new HttpResponse(true, "Resumen del dashboard obtenido correctamente", dashboardService.getDashboardSummary(idEmpresa)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }
}
