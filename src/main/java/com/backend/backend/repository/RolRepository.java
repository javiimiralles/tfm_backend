package com.backend.backend.repository;

import com.backend.backend.models.roles.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface RolRepository extends JpaRepository<Rol, Long>, JpaSpecificationExecutor<Rol> {

    List<Rol> findByIdEmpresa(Long idEmpresa);

    Page<Rol> findAll(Pageable pageable);
}
