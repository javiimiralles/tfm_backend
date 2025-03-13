package com.backend.backend.services.roles;

import com.backend.backend.models.roles.Permiso;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PermisoService {

    List<Permiso> findPermisosByRolId(Long idRol);

    Permiso getPermisoByRolIdAndAccion(Long idRol, Long idAccion);

    @Transactional
    void createPermiso(Permiso permiso);
}
