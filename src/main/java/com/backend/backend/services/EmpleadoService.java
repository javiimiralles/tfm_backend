package com.backend.backend.services;

import com.backend.backend.models.Empleado;
import org.springframework.transaction.annotation.Transactional;

public interface EmpleadoService {

    @Transactional
    void createEmpleado(Empleado empleado);
}
