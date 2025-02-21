package com.backend.backend.repository;

import com.backend.backend.models.clientes.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {
    Page<Cliente> findAll(Pageable pageable);
    List<Cliente> findByIdEmpresa(Long idEmpresa);
}
