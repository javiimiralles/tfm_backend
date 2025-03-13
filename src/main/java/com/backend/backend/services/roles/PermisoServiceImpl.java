package com.backend.backend.services.roles;

import com.backend.backend.models.roles.Permiso;
import com.backend.backend.repository.PermisoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PermisoServiceImpl implements PermisoService {

    private final PermisoRepository permisoRepository;

    Logger logger = Logger.getLogger(PermisoServiceImpl.class.getName());

    public PermisoServiceImpl(PermisoRepository permisoRepository) {
        this.permisoRepository = permisoRepository;
    }

    @Override
    public List<Permiso> findPermisosByRolId(Long idRol) {
        logger.log(Level.INFO ,"Buscando permisos por id del rol:  {}" , idRol);
        return permisoRepository.findByRolId(idRol);
    }

    @Override
    public Permiso getPermisoByRolIdAndAccion(Long idRol, Long idAccion) {
        logger.log(Level.INFO ,"Buscando permiso por id del rol y accion:  {}" , idRol);
        return permisoRepository.findByRolIdAndAccionId(idRol, idAccion);
    }

    @Transactional
    @Override
    public void createPermiso(Permiso permiso) {
        logger.log(Level.INFO ,"Creando permiso:  {}" , permiso);
        permisoRepository.save(permiso);
    }
}
