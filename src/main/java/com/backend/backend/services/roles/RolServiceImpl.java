package com.backend.backend.services.roles;

import com.backend.backend.dto.RolForm;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.RolFilter;
import com.backend.backend.models.roles.Accion;
import com.backend.backend.models.roles.Permiso;
import com.backend.backend.models.roles.Rol;
import com.backend.backend.repository.RolRepository;
import com.backend.backend.specifications.RolSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    private final PermisoService permisoService;

    Logger logger = Logger.getLogger(RolServiceImpl.class.getName());

    public RolServiceImpl(RolRepository rolRepository, PermisoService permisoService) {
        this.rolRepository = rolRepository;
        this.permisoService = permisoService;
    }

    @Override
    public Rol getRolById(Long id) {
        logger.log(Level.INFO ,"Buscando rol por id:  {}" , id);
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public List<Rol> findRolesByIdEmpresa(Long idEmpresa) {
        logger.log(Level.INFO ,"Buscando roles por idEmpresa:  {}" , idEmpresa);
        return rolRepository.findByIdEmpresa(idEmpresa);
    }

    @Override
    public Page<Rol> findRolesByFilter(RolFilter filter) throws BusinessException {
        logger.log(Level.INFO ,"Buscando roles con filtro:  {}" , filter);

        if (filter.getIdEmpresa() == null) {
            logger.log(Level.WARNING ,"El idEmpresa es obligatorio");
            throw new BusinessException("El idEmpresa es obligatorio");
        }

        Specification<Rol> spec = RolSpecification.withFilters(filter);
        Pageable pageable = filter.getPageable();

        return rolRepository.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public Rol createRol(RolForm rolForm) throws BusinessException {
        logger.info("Creando rol: " + rolForm.getNombre());

        Rol rol = new Rol();
        rol.setNombre(rolForm.getNombre());
        rol.setDescripcion(rolForm.getDescripcion());
        rol.setIdEmpresa(rolForm.getIdEmpresa());
        rol = rolRepository.save(rol);

        for (Accion accion : rolForm.getAcciones()) {
            Permiso permiso = new Permiso();
            permiso.setRol(rol);
            permiso.setAccion(accion);
            permisoService.createPermiso(permiso);
        }

        return rol;
    }

    @Transactional
    @Override
    public Rol updateRol(Long idRol, RolForm rolForm) throws BusinessException {
        logger.info("Actualizando rol: " + rolForm.getNombre());

        Rol rol = rolRepository.findById(idRol).orElse(null);
        if (rol == null) {
            logger.log(Level.WARNING ,"Rol no encontrado con id:  {}" , idRol);
            throw new BusinessException("Rol no encontrado con id: " + idRol);
        }

        rol.setNombre(rolForm.getNombre());
        rol.setDescripcion(rolForm.getDescripcion());
        rol.setIdEmpresa(rolForm.getIdEmpresa());
        rol = rolRepository.save(rol);

        for (Accion accion : rolForm.getAcciones()) {
            // Verificar si ya existe el permiso
            Permiso permisoExistente = permisoService.getPermisoByRolIdAndAccion(rol.getId(), accion.getId());
            if (permisoExistente == null) {
                Permiso permiso = new Permiso();
                permiso.setRol(rol);
                permiso.setAccion(accion);
                permisoService.createPermiso(permiso);
            }
        }

        return rol;
    }

}
