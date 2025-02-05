package com.backend.backend.services;

import com.backend.backend.dto.LoginUsuarioForm;
import com.backend.backend.dto.RegistroEmpleadoForm;
import com.backend.backend.exceptions.BadRequestException;
import com.backend.backend.models.*;
import com.backend.backend.utils.JWTUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioService usuarioService;

    private final EmpresaService empresaService;

    private final RolService rolService;

    private final AccionService accionService;

    private final PermisoService permisoService;

    private final EmpleadoService empleadoService;

    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtUtil;

    Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

    public AuthServiceImpl(EmpresaService empresaService, UsuarioService usuarioService,
                          RolService rolService, EmpleadoService empleadoService,
                          AccionService accionService, PermisoService permisoService,
                          PasswordEncoder passwordEncoder, JWTUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.empresaService = empresaService;
        this.rolService = rolService;
        this.accionService = accionService;
        this.permisoService = permisoService;
        this.empleadoService = empleadoService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Transactional
    @Override
    public void registrarEmpleado(RegistroEmpleadoForm registroEmpleadoForm) {
        logger.log(Level.INFO, "Registrando empleado: {}", registroEmpleadoForm.getEmailUsuario());

        // Crear y guardar la empresa a la que pertenece el usuario
        Empresa empresa = new Empresa();
        empresa.setNombre(registroEmpleadoForm.getNombreEmpresa());
        empresa.setRazonSocial(registroEmpleadoForm.getRazonSocialEmpresa());
        empresa.setDireccion(registroEmpleadoForm.getDireccionEmpresa());
        empresa.setTelefono(registroEmpleadoForm.getTelefonoEmpresa());
        empresa.setEmail(registroEmpleadoForm.getEmailEmpresa());
        empresaService.createEmpresa(empresa);

        // Crear y guardar el rol de ADMIN
        Rol rol = new Rol();
        rol.setNombre("ADMIN");
        rol.setDescripcion("Administrador con todos los permisos");
        rol.setIdEmpresa(empresa.getId());
        rolService.createRol(rol);

        // Crear y guardar los permisos por defecto
        List<Accion> acciones = accionService.getAcciones();
        for (Accion accion : acciones) {
            Permiso permiso = new Permiso();
            permiso.setIdRol(rol.getId());
            permiso.setIdAccion(accion.getId());
            permisoService.createPermiso(permiso);
        }

        // Crear y guardar el usuario
        Usuario usuario = new Usuario();
        usuario.setEmail(registroEmpleadoForm.getEmailUsuario());
        usuario.setPassword(passwordEncoder.encode(registroEmpleadoForm.getPasswordUsuario()));
        usuario.setIdRol(rol.getId());
        usuarioService.createUser(usuario);

        // Crear y guardar el empleado asociado al usuario
        Empleado empleado = new Empleado();
        empleado.setIdEmpresa(empresa.getId());
        empleado.setIdUsuario(usuario.getId());
        empleado.setNombre(registroEmpleadoForm.getNombreEmpleado());
        empleado.setApellidos(registroEmpleadoForm.getApellidosEmpleado());
        empleado.setTelefono(registroEmpleadoForm.getTelefonoEmpleado());
        empleado.setDireccion(registroEmpleadoForm.getDireccionEmpleado());
        empleado.setFechaNacimiento(registroEmpleadoForm.getFechaNacimientoEmpleado());
        empleado.setGenero(registroEmpleadoForm.getGeneroEmpleado());
        empleado.setIdRespAlta(usuario.getId());
        empleado.setFechaAlta(new Date());
        empleadoService.createEmpleado(empleado);
    }

    @Override
    public Usuario authenticate(LoginUsuarioForm loginUsuarioForm) {
        logger.log(Level.INFO, "Iniciando sesión: {}", loginUsuarioForm.getEmail());

        Usuario usuario = usuarioService.getUsuarioByEmail(loginUsuarioForm.getEmail());
        if (usuario == null) {
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }

        if (!passwordEncoder.matches(loginUsuarioForm.getPassword(), usuario.getPassword())) {
            throw new BadRequestException("Usuario o contraseña incorrectos");
        }

        return usuario;
    }

    @Override
    public Usuario verifyToken(String token) {
        logger.log(Level.INFO, "Refrescando token de usuario");

        Long idUsuario = jwtUtil.getUserIdFromToken(token);
        Usuario usuario = usuarioService.getUsuarioById(idUsuario);
        if (usuario == null) {
            throw new BadRequestException("Usuario no encontrado");
        }

        return usuario;
    }
}
