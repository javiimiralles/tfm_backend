package com.backend.backend.services.inventario;

import com.backend.backend.dto.ProductoDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.ProductoFilter;
import com.backend.backend.models.inventario.Producto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductoService {
    Producto getProductoById(Long id);

    Page<ProductoDTO> findProductosDTOByFilter(ProductoFilter filter) throws BusinessException;

    @Transactional
    void createProducto(Producto producto, MultipartFile imagen, Long idResponsable) throws IOException;

    @Transactional
    void updateProducto(Long id, Producto producto, MultipartFile imagen, Long idResponsable, boolean imageChanged) throws IOException;
}
