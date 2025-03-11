package com.backend.backend.repository;

import com.backend.backend.models.ventas.HistorialPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialPedidoRepository extends JpaRepository<HistorialPedido, Long> {
}
