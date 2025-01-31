package com.backend.backend.services;

import com.backend.backend.dto.LoginUsuarioDTO;
import com.backend.backend.dto.RegistroEmpleadoDTO;
import com.backend.backend.models.*;
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

    Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

    public AuthServiceImpl(EmpresaService empresaService, UsuarioService usuarioService,
                          RolService rolService, EmpleadoService empleadoService,
                          AccionService accionService, PermisoService permisoService,
                          PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.empresaService = empresaService;
        this.rolService = rolService;
        this.accionService = accionService;
        this.permisoService = permisoService;
        this.empleadoService = empleadoService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public void registrarEmpleado(RegistroEmpleadoDTO registroEmpleadoDTO) {
        logger.log(Level.INFO, "Registrando empleado: {}", registroEmpleadoDTO.getEmailUsuario());

        // Crear y guardar la empresa a la que pertenece el usuario
        Empresa empresa = new Empresa();
        empresa.setNombre(registroEmpleadoDTO.getNombreEmpresa());
        empresa.setRazonSocial(registroEmpleadoDTO.getRazonSocialEmpresa());
        empresa.setDireccion(registroEmpleadoDTO.getDireccionEmpresa());
        empresa.setTelefono(registroEmpleadoDTO.getTelefonoEmpresa());
        empresa.setEmail(registroEmpleadoDTO.getEmailEmpresa());
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
        usuario.setEmail(registroEmpleadoDTO.getEmailUsuario());
        usuario.setPassword(passwordEncoder.encode(registroEmpleadoDTO.getPasswordUsuario()));
        usuario.setIdRol(rol.getId());
        usuarioService.createUser(usuario);

        // Crear y guardar el empleado asociado al usuario
        Empleado empleado = new Empleado();
        empleado.setIdEmpresa(empresa.getId());
        empleado.setIdUsuario(usuario.getId());
        empleado.setNombre(registroEmpleadoDTO.getNombreEmpleado());
        empleado.setApellidos(registroEmpleadoDTO.getApellidosEmpleado());
        empleado.setTelefono(registroEmpleadoDTO.getTelefonoEmpleado());
        empleado.setDireccion(registroEmpleadoDTO.getDireccionEmpleado());
        empleado.setFechaNacimiento(registroEmpleadoDTO.getFechaNacimientoEmpleado());
        empleado.setGenero(registroEmpleadoDTO.getGeneroEmpleado());
        empleado.setIdRespAlta(usuario.getId());
        empleado.setFechaAlta(new Date());
        empleadoService.createEmpleado(empleado);
    }

    @Override
    public Usuario login(LoginUsuarioDTO loginUsuarioDTO) {
        logger.log(Level.INFO, "Iniciando sesión: {}", loginUsuarioDTO.getEmail());
        Usuario usuario = usuarioService.getUsuarioByEmail(loginUsuarioDTO.getEmail());
        if (usuario != null && passwordEncoder.matches(loginUsuarioDTO.getPassword(), usuario.getPassword())) {
            return usuario;
        }
        return null;
    }
}
