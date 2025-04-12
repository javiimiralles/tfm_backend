package com.backend.backend.services.ventas;

import com.backend.backend.filters.FacturaFilter;
import com.backend.backend.models.ventas.Factura;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface FacturaService {
    Factura getFacturaById(Long id);

    Page<Factura> findFacturasByFilter(FacturaFilter filter);

    @Transactional
    byte[] createFactura(Factura factura, Long idResponsable);

    @Transactional
    void updateFactura(Long idFactura, Factura factura, Long idResponsable);
}
