package com.backend.backend.services;

import com.backend.backend.models.Permiso;
import org.springframework.transaction.annotation.Transactional;

public interface PermisoService {

    @Transactional
    void createPermiso(Permiso permiso);
}
