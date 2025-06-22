package com.backend.backend.services.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.backend.dto.LoginUsuarioForm;
import com.backend.backend.dto.UsuarioDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.services.BaseTest;

@SpringBootTest
public class AuthServiceTest extends BaseTest {

    @Autowired
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        setUpBase();
    }

    @Test
    public void testAutheticate() {
        // Autenticamos al usuario registrado
        LoginUsuarioForm loginForm = new LoginUsuarioForm();
        loginForm.setEmail("javi@gmail.com");
        loginForm.setPassword("1234");

        UsuarioDTO usuario = authService.authenticate(loginForm);
        assertNotNull(usuario);
        assertEquals(usuario.getEmail(), loginForm.getEmail());

        // Lo volvemos a autenticar pero con credenciales incorrectas
        loginForm.setEmail("wrongEmail");
        assertThrows(BusinessException.class, () -> {
            authService.authenticate(loginForm);
        }, "Usuario o contraseña incorrectos");

        loginForm.setEmail("javi@gmail.com");
        loginForm.setPassword("wrongPassword");
        assertThrows(BusinessException.class, () -> {
            authService.authenticate(loginForm);
        }, "Usuario o contraseña incorrectos");
    }
    


}
