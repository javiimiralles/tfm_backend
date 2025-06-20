package com.backend.backend.repository;

import com.backend.backend.enums.EstadoPedidoEnum;
import com.backend.backend.models.ventas.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {
    Page<Pedido> findAll(Pageable pageable);

    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.fechaPedido >= :fechaInicio AND p.estado IN (:estados) AND p.idEmpresa = :idEmpresa")
    int countPedidosConFechaLimiteYEstados(@Param("fechaInicio") Date fechaInicio, @Param("idEmpresa") Long idEmpresa, @Param("estados") List<EstadoPedidoEnum> estados);

    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.fechaPedido >= :fechaInicio AND fechaPedido <= :fechaFin AND p.estado = :estado AND p.idEmpresa = :idEmpresa")
    int countPedidosDesdeFechaInicioHastaFechaFinConEstado(
        @Param("fechaInicio") Date fechaInicio,
        @Param("fechaFin") Date fechaFin,
        @Param("idEmpresa") Long idEmpresa,
        @Param("estado") EstadoPedidoEnum estado
    );
}
