package com.backend.backend.services;

import com.backend.backend.dto.ProductoDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.ProductoFilter;
import com.backend.backend.models.Producto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductoService {
    Page<ProductoDTO> findProductosDTOByFilter(ProductoFilter filter) throws BusinessException;

    @Transactional
    void createProducto(Producto producto, MultipartFile imagen, Long idResponsable) throws IOException;
}
