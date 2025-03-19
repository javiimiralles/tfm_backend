package com.backend.backend.services.ventas;

import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.FacturaFilter;
import com.backend.backend.models.ventas.Factura;
import com.backend.backend.repository.FacturaRepository;
import com.backend.backend.specifications.FacturaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;

    public FacturaServiceImpl(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    Logger logger = Logger.getLogger(FacturaServiceImpl.class.getName());

    @Override
    public Factura getFacturaById(Long id) {
        logger.log(Level.INFO, "Obteniendo factura con id: {}", id);
        return facturaRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Factura> findFacturasByFilter(FacturaFilter filter) {
        logger.log(Level.INFO, "Obteniendo facturas con filtro: {}", filter);
        if (filter.getIdEmpresa() == null) {
            logger.log(Level.WARNING, "El idEmpresa es obligatorio");
            throw new BusinessException("El idEmpresa es obligatorio");
        }

        Specification<Factura> spec = FacturaSpecification.withFilters(filter);
        Pageable pageable = filter.getPageable();

        return facturaRepository.findAll(spec, pageable);
    }
}
