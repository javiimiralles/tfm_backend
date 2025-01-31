package com.backend.backend.services;

import com.backend.backend.models.*;
import com.backend.backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Logger;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    Logger logger = Logger.getLogger(UsuarioServiceImpl.class.getName());

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
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
