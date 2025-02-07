package com.backend.backend.repository;

import com.backend.backend.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findByIdEmpresa(Long idEmpresa);
}
