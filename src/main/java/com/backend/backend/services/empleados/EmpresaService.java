package com.backend.backend.services.empleados;

import com.backend.backend.models.empleados.Empresa;
import org.springframework.transaction.annotation.Transactional;

public interface EmpresaService {

    @Transactional
    void createEmpresa(Empresa empresa);
}
