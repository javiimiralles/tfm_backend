package com.backend.backend.services;

import com.backend.backend.models.Empresa;
import org.springframework.transaction.annotation.Transactional;

public interface EmpresaService {

    @Transactional
    void createEmpresa(Empresa empresa);
}
