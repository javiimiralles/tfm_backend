package com.backend.backend.services.inventario;

import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.CategoriaProductoFilter;
import com.backend.backend.models.inventario.CategoriaProducto;
import com.backend.backend.repository.CategoriaProductoRepository;
import com.backend.backend.services.usuarios.UsuarioService;
import com.backend.backend.specifications.CategoriaProductoSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class CategoriaProductoServiceImpl implements CategoriaProductoService {

    private final CategoriaProductoRepository categoriaProductoRepository;

    private final UsuarioService usuarioService;

    Logger logger = Logger.getLogger(CategoriaProductoServiceImpl.class.getName());

    public CategoriaProductoServiceImpl(CategoriaProductoRepository categoriaProductoRepository, UsuarioService usuarioService) {
        this.categoriaProductoRepository = categoriaProductoRepository;
        this.usuarioService = usuarioService;
    }

    @Override
    public CategoriaProducto getCategoriaProductoById(Long id) {
        logger.log(Level.INFO, "Obteniendo categoría de producto con id: {}", id);
        return categoriaProductoRepository.findById(id).orElse(null);
    }

    @Override
    public Page<CategoriaProducto> findCategoriasProductosByFilter(CategoriaProductoFilter filter) {
        logger.log(Level.INFO, "Obteniendo categorías de productos con filtro: {}", filter);
        if (filter.getIdEmpresa() == null) {
            logger.log(Level.WARNING, "El idEmpresa es obligatorio");
            throw new BusinessException("El idEmpresa es obligatorio");
        }

        Specification<CategoriaProducto> spec = CategoriaProductoSpecification.withFilters(filter);
        Pageable pageable = filter.getPageable();

        return categoriaProductoRepository.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public void createCategoriaProducto(CategoriaProducto categoriaProducto, Long idResponsable) throws BusinessException {
        logger.log(Level.INFO, "Creando categoría de producto: {}", categoriaProducto);

        if (!usuarioService.validateUsuarioResponsable(idResponsable, categoriaProducto.getIdEmpresa())) {
            throw new BusinessException("El empleado responsable de creación no existe o no pertenece a la empresa");
        }
        categoriaProductoRepository.save(categoriaProducto);
    }

    @Transactional
    @Override
    public void updateCategoriaProducto(Long id, CategoriaProducto categoriaProducto, Long idResponsable) throws BusinessException {
        logger.log(Level.INFO, "Actualizando categoría de producto: {}", categoriaProducto);

        if (!usuarioService.validateUsuarioResponsable(idResponsable, categoriaProducto.getIdEmpresa())) {
            throw new BusinessException("El empleado responsable de modificación no existe o no pertenece a la empresa");
        }

        CategoriaProducto categoriaProductoToUpdate = categoriaProductoRepository.findById(id).orElse(null);
        if (categoriaProductoToUpdate == null) {
            logger.log(Level.WARNING, "La categoría con id {} no existe", id);
            throw new BusinessException("La categoría de producto no existe");
        }

        categoriaProductoRepository.save(categoriaProducto);
    }


}
