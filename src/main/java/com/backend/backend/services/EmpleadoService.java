package com.backend.backend.services;

import com.backend.backend.dto.EmpleadoDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.EmpleadoFilter;
import com.backend.backend.models.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmpleadoService {

    Empleado getEmpleadoById(Long id);

    Page<EmpleadoDTO> findEmpleadosDTOByFilter(EmpleadoFilter filter) throws BusinessException;

    Empleado getEmpleadoByIdUsuario(Long idUsuario);

    Empleado getEmpleadoByIdUsuarioAndIdEmpresa(Long idUsuario, Long idEmpresa);

    @Transactional
    void createEmpleado(Empleado empleado);
}
