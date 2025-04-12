package com.backend.backend.services.empleados;

import com.backend.backend.models.empleados.Empresa;
import com.backend.backend.repository.EmpresaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.logging.Logger;

@Service
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    Logger logger = Logger.getLogger(EmpresaServiceImpl.class.getName());

    public EmpresaServiceImpl(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    @Override
    public Empresa getEmpresaById(Long id) {
        logger.info("Buscando empresa con ID: " + id);
        return empresaRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void createEmpresa(Empresa empresa) {
        logger.info("Creando empresa: " + empresa.getNombre());
        empresa.setFechaAlta(new Date());
        empresaRepository.save(empresa);
    }
}
