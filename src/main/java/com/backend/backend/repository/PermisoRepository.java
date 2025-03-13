package com.backend.backend.repository;

import com.backend.backend.models.roles.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    List<Permiso> findByRolId(Long id);
    Permiso findByRolIdAndAccionId(Long idRol, Long idAccion);
}
