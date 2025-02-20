package com.backend.backend.services;

import com.backend.backend.dto.ProductoDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.ProductoFilter;
import com.backend.backend.models.Producto;
import com.backend.backend.repository.ProductoRepository;
import com.backend.backend.specifications.ProductoSpecification;
import com.backend.backend.utils.MapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    private final CloudinaryService cloudinaryService;

    private final MapperUtil mapperUtil;

    private static final String FOLDER_PATH_CLOUDINARY = "core/products";

    Logger logger = Logger.getLogger(ProductoServiceImpl.class.getName());

    public ProductoServiceImpl(ProductoRepository productoRepository, CloudinaryService cloudinaryService,
                               MapperUtil mapperUtil) {
        this.productoRepository = productoRepository;
        this.cloudinaryService = cloudinaryService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public Page<ProductoDTO> findProductosDTOByFilter(ProductoFilter filter) throws BusinessException {
        logger.info("Obteniendo productos con filtro: " + filter);

        if (filter.getIdEmpresa() == null) {
            logger.log(Level.WARNING, "El idEmpresa es obligatorio");
            throw new BusinessException("El idEmpresa es obligatorio");
        }

        Specification<Producto> spec = ProductoSpecification.withFilters(filter);
        Pageable pageable = filter.getPageable();

        Page<Producto> productos = productoRepository.findAll(spec, pageable);
        return productos.map(mapperUtil::mapProductoToProductoDTO);
    }

    @Transactional
    @Override
    public void createProducto(Producto producto, MultipartFile imagen, Long idResponsable) throws IOException {
        logger.info("Creando producto: " + producto);

        if (imagen != null && !imagen.isEmpty()) {
            String url = cloudinaryService.uploadImage(imagen, FOLDER_PATH_CLOUDINARY);
            producto.setImagenUrl(url);
        }
        producto.setIdRespAlta(idResponsable);
        producto.setFechaAlta(new Date());
        productoRepository.save(producto);
    }
}
