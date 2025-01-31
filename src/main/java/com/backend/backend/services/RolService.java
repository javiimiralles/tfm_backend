package com.backend.backend.services;

import com.backend.backend.models.Rol;
import org.springframework.transaction.annotation.Transactional;

public interface RolService {

    @Transactional
    void createRol(Rol rol);
}
