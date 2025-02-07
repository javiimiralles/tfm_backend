package com.backend.backend.services;

import com.backend.backend.dto.LoginUsuarioForm;
import com.backend.backend.dto.RegistroEmpleadoForm;
import com.backend.backend.dto.UsuarioDTO;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {

    @Transactional
    void registrarEmpleado(RegistroEmpleadoForm registroEmpleadoForm);

    UsuarioDTO authenticate(LoginUsuarioForm loginUsuarioForm);

    UsuarioDTO verifyToken(String token);
}
