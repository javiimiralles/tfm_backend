package com.backend.backend.repository;

import com.backend.backend.models.proveedores.PedidoProveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PedidoProveedorRepository extends JpaRepository<PedidoProveedor, Long>, JpaSpecificationExecutor<PedidoProveedor> {
    Page<PedidoProveedor> findAll(Pageable pageable);
}
