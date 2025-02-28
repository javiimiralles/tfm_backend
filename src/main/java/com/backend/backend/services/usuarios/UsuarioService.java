package com.backend.backend.services.usuarios;

import com.backend.backend.dto.UsuarioDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.models.usuarios.Usuario;
import org.springframework.transaction.annotation.Transactional;

public interface UsuarioService {

    Usuario getUsuarioById(Long id);

    UsuarioDTO getUsuarioDTOById(Long id);

    Usuario getUsuarioByEmail(String email);

    Usuario createUsuario(Usuario usuario) throws BusinessException;

    void updateUsuario(Long id, Usuario usuario, Long idResponsable) throws BusinessException;

    boolean validateUsuarioResponsable(Long idResponsable, Long idEmpresa);

}
