package com.backend.backend.services;

import com.backend.backend.filters.EmpleadoFilter;
import com.backend.backend.models.Empleado;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmpleadoService {

    Empleado getEmpleadoById(Long id);

    List<Empleado> findEmpleadosByFilter(EmpleadoFilter filter);

    Empleado getEmpleadoByIdUsuario(Long idUsuario);

    Empleado getEmpleadoByIdUsuarioAndIdEmpresa(Long idUsuario, Long idEmpresa);

    @Transactional
    void createEmpleado(Empleado empleado);
}
