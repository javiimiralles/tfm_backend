package com.backend.backend.repository;

import com.backend.backend.models.inventario.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long>, JpaSpecificationExecutor<Producto> {
    List<Producto> findByIdEmpresa(Long idEmpresa);
    Page<Producto> findAll(Pageable pageable);
}
