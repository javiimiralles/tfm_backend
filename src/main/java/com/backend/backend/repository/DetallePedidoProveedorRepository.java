package com.backend.backend.repository;

import com.backend.backend.models.proveedores.DetallePedidoProveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetallePedidoProveedorRepository extends JpaRepository<DetallePedidoProveedor, Long> {
    List<DetallePedidoProveedor> findByIdPedidoProveedor(Long pedidoProveedorId);
}
