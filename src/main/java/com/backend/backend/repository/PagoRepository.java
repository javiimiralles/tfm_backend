package com.backend.backend.repository;

import com.backend.backend.models.ventas.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}
