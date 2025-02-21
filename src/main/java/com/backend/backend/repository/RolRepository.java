package com.backend.backend.repository;

import com.backend.backend.models.roles.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolRepository extends JpaRepository<Rol, Long> {
    List<Rol> findByIdEmpresa(Long idEmpresa);
}
