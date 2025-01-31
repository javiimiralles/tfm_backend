package com.backend.backend.services;

import com.backend.backend.models.Rol;
import com.backend.backend.repository.RolRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    Logger logger = Logger.getLogger(RolServiceImpl.class.getName());

    public RolServiceImpl(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Transactional
    @Override
    public void createRol(Rol rol) {
        logger.info("Creando rol: " + rol.getNombre());
        rolRepository.save(rol);
    }

}
