package com.backend.backend.services.roles;

import com.backend.backend.models.roles.Rol;
import com.backend.backend.repository.RolRepository;
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

    @Transactional
    @Override
    public void createRol(Rol rol) {
        logger.info("Creando rol: " + rol.getNombre());
        rolRepository.save(rol);
    }

}
