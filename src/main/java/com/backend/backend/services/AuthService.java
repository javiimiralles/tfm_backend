package com.backend.backend.services;

import com.backend.backend.dto.LoginUsuarioDTO;
import com.backend.backend.dto.RegistroEmpleadoDTO;
import com.backend.backend.models.Usuario;
import org.springframework.transaction.annotation.Transactional;

public interface AuthService {
    @Transactional
    void registrarEmpleado(RegistroEmpleadoDTO registroEmpleadoDTO);

    Usuario login(LoginUsuarioDTO loginUsuarioDTO);
}
