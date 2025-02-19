package com.backend.backend.services;

import com.backend.backend.models.Rol;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RolService {

    List<Rol> findRolesByIdEmpresa(Long idEmpresa);

    @Transactional
    void createRol(Rol rol);
}
