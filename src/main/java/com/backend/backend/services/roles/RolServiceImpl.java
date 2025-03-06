package com.backend.backend.services.roles;

import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.RolFilter;
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

    Logger logger = Logger.getLogger(RolServiceImpl.class.getName());

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
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
    public void createRol(Rol rol) {
        logger.info("Creando rol: " + rol.getNombre());
        rolRepository.save(rol);
    }

}
