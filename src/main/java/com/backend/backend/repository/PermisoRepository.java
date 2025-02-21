package com.backend.backend.repository;

import com.backend.backend.models.roles.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
}
