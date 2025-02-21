package com.backend.backend.services.paises;

import com.backend.backend.models.paises.Pais;
import com.backend.backend.repository.PaisRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class PaisServiceImpl implements PaisService {

    private final PaisRepository paisRepository;

    Logger logger = Logger.getLogger(PaisServiceImpl.class.getName());

    public PaisServiceImpl(PaisRepository paisRepository) {
        this.paisRepository = paisRepository;
    }

    @Override
    public List<Pais> findPaises() {
        logger.info("Obteniendo lista de paises");
        return paisRepository.findAll();
    }
}
