package com.backend.backend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.backend.backend.dto.RegistroUsuarioForm;
import com.backend.backend.models.empleados.Empleado;
import com.backend.backend.models.usuarios.Usuario;
import com.backend.backend.services.auth.AuthService;
import com.backend.backend.services.empleados.EmpleadoService;
import com.backend.backend.services.usuarios.UsuarioService;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public abstract class BaseTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmpleadoService empleadoService;

    protected Long idUsuarioBase;

    protected Long idEmpresaBase;

    protected Long idEmpleadoBase;
    
    protected void setUpBase() {
        registerUser();

        Usuario usuario = usuarioService.getUsuarioByEmail("javi@gmail.com");
        idUsuarioBase = usuario.getId();

        Empleado empleado = empleadoService.getEmpleadoByIdUsuario(idUsuarioBase);
        idEmpresaBase = empleado.getIdEmpresa();
        idEmpleadoBase = empleado.getId();
    }

    private void registerUser() {
        RegistroUsuarioForm registroForm = new RegistroUsuarioForm();
        registroForm.setNombreEmpleado("Javier");
        registroForm.setApellidosEmpleado("Miralles Martínez");
        registroForm.setEmailUsuario("javi@gmail.com");
        registroForm.setPasswordUsuario("1234");
        registroForm.setNombreEmpresa("Empresa de Prueba");
        registroForm.setRazonSocialEmpresa("Empresa de Prueba S.L.");
        registroForm.setDireccionEmpresa("Calle de la Prueba, 123");
        registroForm.setEmailEmpresa("empresa@gmail.com");

        authService.register(registroForm, null);
    }
}
