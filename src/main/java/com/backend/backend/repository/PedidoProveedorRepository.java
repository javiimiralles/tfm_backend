package com.backend.backend.repository;

import com.backend.backend.enums.EstadoPedidoProveedorEnum;
import com.backend.backend.models.proveedores.PedidoProveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface PedidoProveedorRepository extends JpaRepository<PedidoProveedor, Long>, JpaSpecificationExecutor<PedidoProveedor> {
    Page<PedidoProveedor> findAll(Pageable pageable);

    @Query("SELECT p FROM PedidoProveedor p WHERE p.fechaPedido >= :fechaInicio AND p.estado = :estado AND p.idEmpresa = :idEmpresa")
    List<PedidoProveedor> findPedidosConFechaLimiteYEstado(@Param("fechaInicio") Date fechaInicio, @Param("estado") EstadoPedidoProveedorEnum estado, @Param("idEmpresa") Long idEmpresa);

    @Query("SELECT COUNT(p) FROM PedidoProveedor p WHERE p.fechaPedido >= :fechaInicio AND p.estado IN (:estados) AND p.idEmpresa = :idEmpresa")
    int countPedidosConFechaLimiteYEstados(@Param("fechaInicio") Date fechaInicio, @Param("idEmpresa") Long idEmpresa, @Param("estados") List<EstadoPedidoProveedorEnum> estados);



}
