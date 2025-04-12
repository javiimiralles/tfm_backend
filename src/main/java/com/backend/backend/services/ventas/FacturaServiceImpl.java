package com.backend.backend.services.ventas;

import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.FacturaFilter;
import com.backend.backend.models.ventas.Factura;
import com.backend.backend.models.ventas.NumeradorFactura;
import com.backend.backend.repository.FacturaRepository;
import com.backend.backend.repository.NumeradorFacturaRepository;
import com.backend.backend.services.pdf.PdfGeneratorService;
import com.backend.backend.services.usuarios.UsuarioService;
import com.backend.backend.specifications.FacturaSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class FacturaServiceImpl implements FacturaService {

    private final FacturaRepository facturaRepository;

    private final NumeradorFacturaRepository numeradorFacturaRepository;

    private final UsuarioService usuarioService;

    private final PdfGeneratorService pdfGeneratorService;

    public FacturaServiceImpl(FacturaRepository facturaRepository, NumeradorFacturaRepository numeradorFacturaRepository,
                              UsuarioService usuarioService, PdfGeneratorService pdfGeneratorService) {
        this.facturaRepository = facturaRepository;
        this.numeradorFacturaRepository = numeradorFacturaRepository;
        this.usuarioService = usuarioService;
        this.pdfGeneratorService = pdfGeneratorService;
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

    @Transactional
    @Override
    public byte[] createFactura(Factura factura, Long idResponsable) {
        logger.log(Level.INFO, "Creando factura: {}", factura);

        if (!usuarioService.validateUsuarioResponsable(idResponsable, factura.getIdEmpresa())) {
            throw new BusinessException("El empleado responsable de creación no existe o no pertenece a la empresa");
        }

        Integer currentYear = Calendar.getInstance().get(Calendar.YEAR);
        NumeradorFactura numeradorFactura = numeradorFacturaRepository.findByYearAndIdEmpresa(currentYear, factura.getIdEmpresa());
        if (numeradorFactura == null) {
            numeradorFactura = new NumeradorFactura();
            numeradorFactura.setYear(currentYear);
            numeradorFactura.setIdEmpresa(factura.getIdEmpresa());
            numeradorFactura.setUltimoNumero(1);
        } else {
            numeradorFactura.setUltimoNumero(numeradorFactura.getUltimoNumero() + 1);
        }
        numeradorFacturaRepository.save(numeradorFactura);

        String numeroFactura = String.format("FAC-%d-%05d", currentYear, numeradorFactura.getUltimoNumero());
        factura.setNumeroFactura(numeroFactura);
        factura.setFechaFactura(new Date());
        facturaRepository.save(factura);

        return pdfGeneratorService.generateFacturaPdf(factura.getId());
    }

    @Transactional
    @Override
    public void updateFactura(Long idFactura, Factura factura, Long idResponsable) {
        logger.log(Level.INFO, "Actualizando factura: {}", factura);

        if (!usuarioService.validateUsuarioResponsable(idResponsable, factura.getIdEmpresa())) {
            throw new BusinessException("El empleado responsable de actualización no existe o no pertenece a la empresa");
        }

        Factura facturaToUpdate = facturaRepository.findById(idFactura).orElse(null);
        if (facturaToUpdate == null) {
            throw new BusinessException("La factura a actualizar no existe");
        }

        facturaToUpdate.setFechaFactura(factura.getFechaFactura());
        facturaToUpdate.setFechaVencimiento(factura.getFechaVencimiento());
        facturaRepository.save(factura);
    }

    
}
