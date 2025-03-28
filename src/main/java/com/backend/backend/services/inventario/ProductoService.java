package com.backend.backend.services.inventario;

import com.backend.backend.dto.ProductoDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.ProductoFilter;
import com.backend.backend.models.inventario.Producto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductoService {
    Producto getProductoById(Long id);

    List<Producto> findProductosByEmpresa(Long idEmpresa);

    Page<ProductoDTO> findProductosDTOByFilter(ProductoFilter filter) throws BusinessException;

    BigDecimal getCosteProducto(Long id);

    @Transactional
    void createProducto(Producto producto, MultipartFile imagen, Long idResponsable) throws IOException;

    @Transactional
    void updateProducto(Long id, Producto producto, MultipartFile imagen, Long idResponsable, boolean imageChanged) throws IOException;

    @Transactional
    void updateStockProducto(Long id, int cantidad);
}
