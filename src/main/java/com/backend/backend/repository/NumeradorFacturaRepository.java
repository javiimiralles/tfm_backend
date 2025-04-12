package com.backend.backend.repository;

import com.backend.backend.models.ventas.NumeradorFactura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NumeradorFacturaRepository extends JpaRepository<NumeradorFactura, Long> {
    NumeradorFactura findByYearAndIdEmpresa(int year, Long idEmpresa);
}
