package com.backend.backend.services.inventario;

import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.CategoriaProductoFilter;
import com.backend.backend.models.inventario.CategoriaProducto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface CategoriaProductoService {
    CategoriaProducto getCategoriaProductoById(Long id);

    Page<CategoriaProducto> findCategoriasProductosByFilter(CategoriaProductoFilter filter);

    @Transactional
    void createCategoriaProducto(CategoriaProducto categoriaProducto, Long idResponsable) throws BusinessException;

    @Transactional
    void updateCategoriaProducto(Long id, CategoriaProducto categoriaProducto, Long idResponsable) throws BusinessException;
}
