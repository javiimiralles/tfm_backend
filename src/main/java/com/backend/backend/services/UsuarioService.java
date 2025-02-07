package com.backend.backend.services;

import com.backend.backend.dto.UsuarioDTO;
import com.backend.backend.models.Usuario;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioService {

    UsuarioDTO getUsuarioDTOById(Long id);

    Usuario getUsuarioByEmail(String email);

    @Transactional
    void createUser(Usuario usuario);
}
