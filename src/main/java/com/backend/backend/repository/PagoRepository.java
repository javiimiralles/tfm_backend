package com.backend.backend.repository;

import com.backend.backend.models.ventas.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    @Query("SELECT p FROM Pago p WHERE p.fechaPago >= :fechaInicio AND p.idEmpresa = :idEmpresa")
    List<Pago> findPagosConFechaLimite(@Param("fechaInicio") Date fechaInicio, @Param("idEmpresa") Long idEmpresa);
}
