package com.backend.backend.services;

import com.backend.backend.dto.LoginUsuarioForm;
import com.backend.backend.dto.RegistroUsuarioForm;
import com.backend.backend.dto.UsuarioDTO;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {

    @Transactional
    void registrarEmpleado(RegistroUsuarioForm registroUsuarioForm);

    UsuarioDTO authenticate(LoginUsuarioForm loginUsuarioForm);

    UsuarioDTO verifyToken(String token);
}
