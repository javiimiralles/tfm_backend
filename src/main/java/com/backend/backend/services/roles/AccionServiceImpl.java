package com.backend.backend.services.roles;

import com.backend.backend.models.roles.Accion;
import com.backend.backend.repository.AccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class AccionServiceImpl implements AccionService {

    private final AccionRepository accionRepository;

    Logger logger = Logger.getLogger(AccionServiceImpl.class.getName());

    public AccionServiceImpl(AccionRepository accionRepository) {
        this.accionRepository = accionRepository;
    }

    @Override
    public List<Accion> getAcciones() {
        logger.info("Obteniendo acciones");
        return accionRepository.findAll();
    }
}
