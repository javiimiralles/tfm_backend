package com.backend.backend.services.auth;

import com.backend.backend.dto.LoginUsuarioForm;
import com.backend.backend.dto.RegistroUsuarioForm;
import com.backend.backend.dto.UsuarioDTO;
import com.backend.backend.exceptions.BusinessException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {

    @Transactional
    void register(RegistroUsuarioForm registroUsuarioForm, MultipartFile imagen) throws BusinessException;

    UsuarioDTO authenticate(LoginUsuarioForm loginUsuarioForm);

    UsuarioDTO verifyToken(String token);
}
