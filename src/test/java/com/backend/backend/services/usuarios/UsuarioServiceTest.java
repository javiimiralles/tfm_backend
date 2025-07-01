package com.backend.backend.services.usuarios;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.backend.backend.dto.UsuarioDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.models.usuarios.Usuario;
import com.backend.backend.services.BaseTest;

public class UsuarioServiceTest extends BaseTest {

    @Autowired
    private UsuarioService usuarioService;

    @BeforeEach
    public void setUp() {
        setUpBase();
    }

    @Test
    public void testGetUsuarioById() {
        Usuario usuario = usuarioService.getUsuarioById(idUsuarioBase);
        assertNotNull(usuario);
        assertEquals(usuario.getId(), idUsuarioBase);
        assertEquals(usuario.getEmail(), "javi@gmail.com");
    }

    @Test
    public void testGetUsuarioDTOById() {
        UsuarioDTO usuarioDTO = usuarioService.getUsuarioDTOById(idUsuarioBase);
        assertNotNull(usuarioDTO);
        assertEquals(usuarioDTO.getId(), idUsuarioBase);
        assertEquals(usuarioDTO.getEmail(), "javi@gmail.com");
    }

    @Test
    public void testGetUsuarioByEmail() {
        Usuario usuario = usuarioService.getUsuarioByEmail("javi@gmail.com");
        assertNotNull(usuario);
        assertEquals(usuario.getEmail(), "javi@gmail.com");
    }

    @Test
    public void testCreateUsuario() {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail("javi@gmail.com");

        assertThrows(BusinessException.class, () -> {
            usuarioService.createUsuario(nuevoUsuario);
        }, "El email ya está en uso");
    }

}
