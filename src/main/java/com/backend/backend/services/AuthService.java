package com.backend.backend.services;

import com.backend.backend.dto.LoginUsuarioForm;
import com.backend.backend.dto.RegistroEmpleadoForm;
import com.backend.backend.models.Usuario;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {
    @Transactional
    void registrarEmpleado(RegistroEmpleadoForm registroEmpleadoForm);

    Usuario authenticate(LoginUsuarioForm loginUsuarioForm);

    Usuario verifyToken(String token);
}
