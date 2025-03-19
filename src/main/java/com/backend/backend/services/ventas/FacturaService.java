package com.backend.backend.services.ventas;

import com.backend.backend.filters.FacturaFilter;
import com.backend.backend.models.ventas.Factura;
import org.springframework.data.domain.Page;

public interface FacturaService {
    Factura getFacturaById(Long id);

    Page<Factura> findFacturasByFilter(FacturaFilter filter);
}
