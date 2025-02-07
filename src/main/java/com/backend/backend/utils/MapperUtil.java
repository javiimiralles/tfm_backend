package com.backend.backend.utils;

import com.backend.backend.dto.UsuarioDTO;
import com.backend.backend.models.Usuario;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {

    public UsuarioDTO mapUsuarioToUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setRol(usuario.getRol().getNombre());
        usuarioDTO.setPermisos(usuario.getAccionesPermitidas());
        return usuarioDTO;
    }
}
