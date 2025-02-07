package com.backend.backend.services;

import com.backend.backend.filters.EmpleadoFilter;
import com.backend.backend.models.Empleado;
import com.backend.backend.repository.EmpleadoRepository;
import com.backend.backend.specifications.EmpleadoSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    Logger logger = Logger.getLogger(EmpleadoServiceImpl.class.getName());

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public Empleado getEmpleadoById(Long id) {
        logger.log(Level.INFO, "Obteniendo empleado por id: {}", id);
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Empleado> findEmpleadosByFilter(EmpleadoFilter filter) {
        logger.log(Level.INFO, "Obteniendo empleados por filtro: {}", filter);
        Specification<Empleado> spec = EmpleadoSpecification.withFilters(filter);
        return empleadoRepository.findAll(spec);
    }

    @Override
    public Empleado getEmpleadoByIdUsuario(Long idUsuario) {
        logger.log(Level.INFO, "Obteniendo empleado por idUsuario: {}", idUsuario);
        return empleadoRepository.findByIdUsuario(idUsuario);
    }

    @Override
    public Empleado getEmpleadoByIdUsuarioAndIdEmpresa(Long idUsuario, Long idEmpresa) {
        logger.log(Level.INFO, "Obteniendo empleado por idUsuario: {} y idEmpresa: {}", new Object[]{idUsuario, idEmpresa});
        return empleadoRepository.findByIdUsuarioAndIdEmpresa(idUsuario, idEmpresa);
    }

    @Transactional
    @Override
    public void createEmpleado(Empleado empleado) {
        logger.info("Creando empleado: " + empleado.getNombre());
        empleadoRepository.save(empleado);
    }
}
