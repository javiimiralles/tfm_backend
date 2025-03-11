package com.backend.backend.repository;

import com.backend.backend.models.ventas.HistorialCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialCompraRepository extends JpaRepository<HistorialCompra, Long> {
}
