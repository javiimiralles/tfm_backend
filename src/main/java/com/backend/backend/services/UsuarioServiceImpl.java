package com.backend.backend.services;

import com.backend.backend.dto.UsuarioDTO;
import com.backend.backend.models.*;
import com.backend.backend.repository.UsuarioRepository;
import com.backend.backend.utils.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final MapperUtil mapperUtil;

    Logger logger = Logger.getLogger(UsuarioServiceImpl.class.getName());

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, MapperUtil mapperUtil) {
        this.usuarioRepository = usuarioRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public UsuarioDTO getUsuarioDTOById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) return null;

        return mapperUtil.mapUsuarioToUsuarioDTO(usuario);
    }

    @Override
    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public void createUser(Usuario usuario) {
        logger.info("Creando usuario: " + usuario.getEmail());
        usuarioRepository.save(usuario);
    }

}
