package com.backend.backend.services.roles;

import com.backend.backend.models.roles.Rol;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RolService {

    List<Rol> findRolesByIdEmpresa(Long idEmpresa);

    @Transactional
    void createRol(Rol rol);
}
