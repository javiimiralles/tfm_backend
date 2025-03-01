package com.backend.backend.repository;

import com.backend.backend.models.proveedores.PedidoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoProveedorRepository extends JpaRepository<PedidoProveedor, Long> {
}
