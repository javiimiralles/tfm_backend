package com.backend.backend.services.proveedores;

import com.backend.backend.dto.ProveedorDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.ProveedorFilter;
import com.backend.backend.models.proveedores.Proveedor;
import com.backend.backend.repository.ProveedorRepository;
import com.backend.backend.services.usuarios.UsuarioService;
import com.backend.backend.specifications.ProveedorSpecification;
import com.backend.backend.utils.MapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    private final UsuarioService usuarioService;

    private final MapperUtil mapperUtil;

    Logger logger = Logger.getLogger(ProveedorServiceImpl.class.getName());

    public ProveedorServiceImpl(ProveedorRepository proveedorRepository, MapperUtil mapperUtil, UsuarioService usuarioService) {
        this.proveedorRepository = proveedorRepository;
        this.mapperUtil = mapperUtil;
        this.usuarioService = usuarioService;
    }

    @Override
    public Proveedor getProveedorById(Long id) {
        logger.log(Level.INFO, "Buscando proveedor con id: {0}", id);
        return proveedorRepository.findById(id).orElse(null);
    }

    @Override
    public Page<ProveedorDTO> findProveedoresDTOByFilter(ProveedorFilter filter) throws BusinessException {
        logger.log(Level.INFO, "Buscando proveedores con filtro: {0}", filter);

        if (filter.getIdEmpresa() == null) {
            logger.log(Level.WARNING, "El idEmpresa es obligatorio");
            throw new BusinessException("El idEmpresa es obligatorio");
        }

        Specification<Proveedor> spec = ProveedorSpecification.withFilters(filter);
        Pageable pageable = filter.getPageable();

        Page<Proveedor> proveedores = proveedorRepository.findAll(spec, pageable);
        return proveedores.map(mapperUtil::mapProveedorToProveedorDTO);
    }

    @Transactional
    @Override
    public void createProveedor(Proveedor proveedor, Long idResponsable) {
        logger.log(Level.INFO, "Creando proveedor: {0}", proveedor);

        if (!usuarioService.validateUsuarioResponsable(idResponsable, proveedor.getIdEmpresa())) {
            throw new BusinessException("El usuario responsable no existe o no pertenece a la empresa");
        }

        proveedor.setFechaAlta(new Date());
        proveedor.setIdRespAlta(idResponsable);
        proveedorRepository.save(proveedor);
    }

    @Transactional
    @Override
    public void updateProveedor(Long id, Proveedor proveedor, Long idResponsable) {
        logger.log(Level.INFO, "Actualizando proveedor con id: {0}", id);

        Proveedor proveedorToUpdate = proveedorRepository.findById(id).orElse(null);
        if (proveedorToUpdate == null) {
            throw new BusinessException("El proveedor no existe");
        }

        if (!usuarioService.validateUsuarioResponsable(idResponsable, proveedor.getIdEmpresa())) {
            throw new BusinessException("El usuario responsable no existe o no pertenece a la empresa");
        }

        proveedor.setIdRespModif(idResponsable);
        proveedor.setFechaModif(new Date());
        proveedorRepository.save(proveedor);
    }

    @Transactional
    @Override
    public void deleteProveedor(Long id, Long idResponsable) {
        logger.log(Level.INFO, "Eliminando proveedor con id: {0}", id);

        Proveedor proveedorToDelete = proveedorRepository.findById(id).orElse(null);
        if (proveedorToDelete == null) {
            throw new BusinessException("El proveedor no existe");
        }

        if (!usuarioService.validateUsuarioResponsable(idResponsable, proveedorToDelete.getIdEmpresa())) {
            throw new BusinessException("El usuario responsable no existe o no pertenece a la empresa");
        }

        proveedorToDelete.setFechaBaja(new Date());
        proveedorToDelete.setIdRespBaja(idResponsable);
        proveedorRepository.save(proveedorToDelete);
    }
}
