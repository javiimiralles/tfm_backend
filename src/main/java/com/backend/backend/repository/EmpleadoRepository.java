package com.backend.backend.repository;

import com.backend.backend.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long>, JpaSpecificationExecutor<Empleado> {
    Empleado findByIdUsuario(Long idUsuario);
    Empleado findByIdUsuarioAndIdEmpresa(Long idUsuario, Long idEmpresa);
}
