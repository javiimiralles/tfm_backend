package com.backend.backend.services.inventario;

import com.backend.backend.dto.ProductoDTO;
import com.backend.backend.enums.RutasCloudinaryEnum;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.ProductoFilter;
import com.backend.backend.models.inventario.Producto;
import com.backend.backend.repository.ProductoRepository;
import com.backend.backend.services.cloudinary.CloudinaryService;
import com.backend.backend.services.usuarios.UsuarioService;
import com.backend.backend.specifications.ProductoSpecification;
import com.backend.backend.utils.MapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    private final CloudinaryService cloudinaryService;

    private final UsuarioService usuarioService;

    private final MapperUtil mapperUtil;

    Logger logger = Logger.getLogger(ProductoServiceImpl.class.getName());

    public ProductoServiceImpl(ProductoRepository productoRepository, CloudinaryService cloudinaryService,
                               MapperUtil mapperUtil, UsuarioService usuarioService) {
        this.productoRepository = productoRepository;
        this.cloudinaryService = cloudinaryService;
        this.mapperUtil = mapperUtil;
        this.usuarioService = usuarioService;
    }

    @Override
    public Producto getProductoById(Long id) {
        logger.log(Level.INFO, "Buscando producto con id: {0}", id);
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ProductoDTO> findProductosDTOByFilter(ProductoFilter filter) throws BusinessException {
        logger.log(Level.INFO, "Buscando productos con filtro: {0}", filter);

        if (filter.getIdEmpresa() == null) {
            logger.log(Level.WARNING, "El idEmpresa es obligatorio");
            throw new BusinessException("El idEmpresa es obligatorio");
        }

        Specification<Producto> spec = ProductoSpecification.withFilters(filter);
        Pageable pageable = filter.getPageable();

        Page<Producto> productos = productoRepository.findAll(spec, pageable);
        return productos.map(mapperUtil::mapProductoToProductoDTO);
    }

    @Override
    public BigDecimal getCosteProducto(Long id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto == null) {
            throw new BusinessException("El producto no existe");
        }
        return producto.getCoste();
    }

    @Transactional
    @Override
    public void createProducto(Producto producto, MultipartFile imagen, Long idResponsable) throws IOException {
        logger.log(Level.INFO, "Creando producto: {0}", producto);

        if (!usuarioService.validateUsuarioResponsable(idResponsable, producto.getIdEmpresa())) {
            throw new BusinessException("El usuario responsable no existe o no pertenece a la empresa");
        }

        if (imagen != null && !imagen.isEmpty()) {
            String url = cloudinaryService.uploadImage(imagen, RutasCloudinaryEnum.PATH_IMAGEN_PRODUCTOS.getRuta());
            producto.setImagenUrl(url);
        }
        producto.setIdRespAlta(idResponsable);
        producto.setFechaAlta(new Date());
        productoRepository.save(producto);
    }

    @Transactional
    @Override
    public void updateProducto(Long id, Producto producto, MultipartFile imagen, Long idResponsable, boolean imageChanged) throws IOException {
        logger.log(Level.INFO, "Actualizando producto: {0}", producto);

        Producto productoToUpdate = productoRepository.findById(id).orElse(null);
        if (productoToUpdate == null) {
            throw new BusinessException("El producto no existe");
        }

        if (!usuarioService.validateUsuarioResponsable(idResponsable, producto.getIdEmpresa())) {
            throw new BusinessException("El usuario responsable no existe o no pertenece a la empresa");
        }

        if (imageChanged) {
            if (imagen != null && !imagen.isEmpty()) {
                String url = cloudinaryService.uploadImage(imagen, RutasCloudinaryEnum.PATH_IMAGEN_PRODUCTOS.getRuta());
                producto.setImagenUrl(url);
            } else {
                producto.setImagenUrl(null);
            }
        }

        producto.setFechaModif(new Date());
        producto.setIdRespModif(idResponsable);
        productoRepository.save(producto);
    }
}
