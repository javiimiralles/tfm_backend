package com.backend.backend.repository;

import com.backend.backend.models.empleados.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
