package com.backend.backend.services;

import com.backend.backend.models.Permiso;
import com.backend.backend.repository.PermisoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
public class PermisoServiceImpl implements PermisoService {

    private final PermisoRepository permisoRepository;

    Logger logger = Logger.getLogger(PermisoServiceImpl.class.getName());

    public PermisoServiceImpl(PermisoRepository permisoRepository) {
        this.permisoRepository = permisoRepository;
    }

    @Transactional
    @Override
    public void createPermiso(Permiso permiso) {
        permisoRepository.save(permiso);
    }
}
