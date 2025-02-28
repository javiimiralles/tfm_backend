package com.backend.backend.services.proveedores;

import com.backend.backend.dto.ProveedorDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.ProveedorFilter;
import com.backend.backend.models.proveedores.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface ProveedorService {
    Proveedor getProveedorById(Long id);

    Page<ProveedorDTO> findProveedoresDTOByFilter(ProveedorFilter filter) throws BusinessException;

    @Transactional
    void createProveedor(Proveedor proveedor, Long idResponsable);

    @Transactional
    void updateProveedor(Long id, Proveedor proveedor, Long idResponsable);

    @Transactional
    void deleteProveedor(Long id, Long idResponsable);
}
