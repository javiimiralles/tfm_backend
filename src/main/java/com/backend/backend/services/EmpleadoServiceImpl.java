package com.backend.backend.services;

import com.backend.backend.models.Empleado;
import com.backend.backend.repository.EmpleadoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    Logger logger = Logger.getLogger(EmpleadoServiceImpl.class.getName());

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Transactional
    @Override
    public void createEmpleado(Empleado empleado) {
        logger.info("Creando empleado: " + empleado.getNombre());
        empleadoRepository.save(empleado);
    }
}
