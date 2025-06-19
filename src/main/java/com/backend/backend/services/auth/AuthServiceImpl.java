package com.backend.backend.services.auth;

import com.backend.backend.dto.LoginUsuarioForm;
import com.backend.backend.dto.RegistroUsuarioForm;
import com.backend.backend.dto.RolForm;
import com.backend.backend.dto.UsuarioDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.models.empleados.Empleado;
import com.backend.backend.models.empleados.Empresa;
import com.backend.backend.models.roles.Rol;
import com.backend.backend.models.usuarios.Usuario;
import com.backend.backend.services.empleados.EmpleadoService;
import com.backend.backend.services.empleados.EmpresaService;
import com.backend.backend.services.roles.AccionService;
import com.backend.backend.services.roles.RolService;
import com.backend.backend.services.usuarios.UsuarioService;
import com.backend.backend.utils.JWTUtil;
import com.backend.backend.utils.MapperUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioService usuarioService;

    private final EmpresaService empresaService;

    private final RolService rolService;

    private final AccionService accionService;

    private final EmpleadoService empleadoService;

    private final PasswordEncoder passwordEncoder;

    private final JWTUtil jwtUtil;

    private final MapperUtil mapperUtil;

    Logger logger = Logger.getLogger(AuthServiceImpl.class.getName());

    public AuthServiceImpl(EmpresaService empresaService, UsuarioService usuarioService,
            RolService rolService, EmpleadoService empleadoService,
            AccionService accionService, PasswordEncoder passwordEncoder,
            JWTUtil jwtUtil, MapperUtil mapperUtil) {
        this.usuarioService = usuarioService;
        this.empresaService = empresaService;
        this.rolService = rolService;
        this.accionService = accionService;
        this.empleadoService = empleadoService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.mapperUtil = mapperUtil;
    }

    @Transactional
    @Override
    public void register(RegistroUsuarioForm registroUsuarioForm, MultipartFile imagen) throws BusinessException {
        logger.log(Level.INFO, "Registrando empleado: {}", registroUsuarioForm.getEmailUsuario());

        try {

            // Crear y guardar la empresa a la que pertenece el usuario
            Empresa empresa = new Empresa();
            empresa.setNombre(registroUsuarioForm.getNombreEmpresa());
            empresa.setRazonSocial(registroUsuarioForm.getRazonSocialEmpresa());
            empresa.setDireccion(registroUsuarioForm.getDireccionEmpresa());
            empresa.setTelefono(registroUsuarioForm.getTelefonoEmpresa());
            empresa.setEmail(registroUsuarioForm.getEmailEmpresa());
            empresaService.createEmpresa(empresa);

            // Crear y guardar el rol de ADMIN
            RolForm rolForm = new RolForm();
            rolForm.setNombre("ADMIN");
            rolForm.setDescripcion("Administrador con todos los permisos");
            rolForm.setIdEmpresa(empresa.getId());
            rolForm.setAcciones(accionService.getAcciones());
            Rol rol = rolService.createRol(rolForm);

            // Crear el usuario
            Usuario usuario = new Usuario();
            usuario.setEmail(registroUsuarioForm.getEmailUsuario());
            usuario.setPassword(registroUsuarioForm.getPasswordUsuario());
            usuario.setRol(rol);

            // Crear y guardar el empleado asociado al usuario
            Empleado empleado = new Empleado();
            empleado.setIdEmpresa(empresa.getId());
            empleado.setUsuario(usuario);
            empleado.setNombre(registroUsuarioForm.getNombreEmpleado());
            empleado.setApellidos(registroUsuarioForm.getApellidosEmpleado());
            empleado.setTelefono(registroUsuarioForm.getTelefonoEmpleado());
            empleado.setDireccion(registroUsuarioForm.getDireccionEmpleado());
            empleado.setFechaNacimiento(registroUsuarioForm.getFechaNacimientoEmpleado());
            empleado.setGenero(registroUsuarioForm.getGeneroEmpleado());
            empleadoService.createEmpleado(empleado, usuario, imagen, null);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al registrar empleado: {}", e.getMessage());
            throw new BusinessException("Error al registrar empleado");
        }
    }

    @Override
    public UsuarioDTO authenticate(LoginUsuarioForm loginUsuarioForm) {
        logger.log(Level.INFO, "Iniciando sesión: {}", loginUsuarioForm.getEmail());

        Usuario usuario = usuarioService.getUsuarioByEmail(loginUsuarioForm.getEmail());
        if (usuario == null) {
            throw new BusinessException("Usuario o contraseña incorrectos");
        }

        if (!passwordEncoder.matches(loginUsuarioForm.getPassword(), usuario.getPassword())) {
            throw new BusinessException("Usuario o contraseña incorrectos");
        }

        return mapperUtil.mapUsuarioToUsuarioDTO(usuario);
    }

    @Override
    public UsuarioDTO verifyToken(String token) {
        logger.log(Level.INFO, "Refrescando token de usuario");

        Claims claims = jwtUtil.extractClaimsFromToken(token);
        Long idUsuario = Long.parseLong(claims.getSubject());
        UsuarioDTO usuario = usuarioService.getUsuarioDTOById(idUsuario);
        if (usuario == null) {
            throw new BusinessException("Usuario no encontrado");
        }

        return usuario;
    }
}
