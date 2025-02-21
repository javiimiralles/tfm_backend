package com.backend.backend.services.roles;

import com.backend.backend.models.roles.Permiso;
import org.springframework.transaction.annotation.Transactional;

public interface PermisoService {

    @Transactional
    void createPermiso(Permiso permiso);
}
