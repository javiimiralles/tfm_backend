package com.backend.backend.services.usuarios;

import com.backend.backend.dto.UsuarioDTO;
import com.backend.backend.models.usuarios.Usuario;
import com.backend.backend.repository.UsuarioRepository;
import com.backend.backend.services.empleados.EmpleadoService;
import com.backend.backend.utils.MapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final EmpleadoService empleadoService;

    private final MapperUtil mapperUtil;

    Logger logger = Logger.getLogger(UsuarioServiceImpl.class.getName());

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, MapperUtil mapperUtil, EmpleadoService empleadoService) {
        this.usuarioRepository = usuarioRepository;
        this.mapperUtil = mapperUtil;
        this.empleadoService = empleadoService;
    }

    @Override
    public UsuarioDTO getUsuarioDTOById(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) return null;

        return mapperUtil.mapUsuarioToUsuarioDTO(usuario);
    }

    @Override
    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public void createUser(Usuario usuario) {
        logger.info("Creando usuario: " + usuario.getEmail());
        usuarioRepository.save(usuario);
    }

    @Override
    public boolean validateUsuarioResponsable(Long idResponsable, Long idEmpresa) {
        if (userNotExists(idResponsable)) {
            logger.warning("El usuario responsable no existe");
            return false;
        }
        if (validateEmployee(idEmpresa, idResponsable)) {
            logger.log(Level.WARNING, "El empleado con idUsuario {} no pertenece a la empresa {}", new Object[]{idResponsable, idEmpresa});
            return false;
        }
        return true;
    }

    private boolean userNotExists(Long id) {
        return getUsuarioDTOById(id) == null;
    }

    private boolean validateEmployee(Long idEmpresa, Long idUsuario) {
        return empleadoService.getEmpleadoByIdUsuarioAndIdEmpresa(idUsuario, idEmpresa) == null;
    }

}
