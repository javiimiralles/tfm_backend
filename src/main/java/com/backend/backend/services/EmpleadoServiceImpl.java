package com.backend.backend.services;

import com.backend.backend.dto.EmpleadoDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.EmpleadoFilter;
import com.backend.backend.models.Empleado;
import com.backend.backend.repository.EmpleadoRepository;
import com.backend.backend.specifications.EmpleadoSpecification;
import com.backend.backend.utils.MapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    private final MapperUtil mapperUtil;

    Logger logger = Logger.getLogger(EmpleadoServiceImpl.class.getName());

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository, MapperUtil mapperUtil) {
        this.empleadoRepository = empleadoRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public Empleado getEmpleadoById(Long id) {
        logger.log(Level.INFO, "Obteniendo empleado por id: {}", id);
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public Page<EmpleadoDTO> findEmpleadosDTOByFilter(EmpleadoFilter filter) {
        logger.log(Level.INFO, "Obteniendo empleados por filtro: {}", filter);
        if (filter.getIdEmpresa() == null) {
            logger.log(Level.WARNING, "El idEmpresa es obligatorio");
            throw new BusinessException("El idEmpresa es obligatorio");
        }
        Specification<Empleado> spec = EmpleadoSpecification.withFilters(filter);
        Pageable pageable = filter.getPageable();

        Page<Empleado> empleados = empleadoRepository.findAll(spec, pageable);
        return empleados.map(mapperUtil::mapEmpleadoToEmpleadoDTO);
    }

    @Override
    public Empleado getEmpleadoByIdUsuario(Long idUsuario) {
        logger.log(Level.INFO, "Obteniendo empleado por idUsuario: {}", idUsuario);
        return empleadoRepository.findByUsuarioId(idUsuario);
    }

    @Override
    public Empleado getEmpleadoByIdUsuarioAndIdEmpresa(Long idUsuario, Long idEmpresa) {
        logger.log(Level.INFO, "Obteniendo empleado por idUsuario: {} y idEmpresa: {}", new Object[]{idUsuario, idEmpresa});
        return empleadoRepository.findByUsuarioIdAndIdEmpresa(idUsuario, idEmpresa);
    }

    @Transactional
    @Override
    public void createEmpleado(Empleado empleado) {
        logger.info("Creando empleado: " + empleado.getNombre());
        empleadoRepository.save(empleado);
    }
}
