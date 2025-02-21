package com.backend.backend.repository;

import com.backend.backend.models.empleados.Empleado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long>, JpaSpecificationExecutor<Empleado> {
    Page<Empleado> findAll(Pageable pageable);
    Empleado findByUsuarioId(Long idUsuario);
    Empleado findByUsuarioIdAndIdEmpresa(Long idUsuario, Long idEmpresa);
}
