package com.backend.backend.repository;

import com.backend.backend.models.roles.Accion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccionRepository extends JpaRepository<Accion, Long> {
}
