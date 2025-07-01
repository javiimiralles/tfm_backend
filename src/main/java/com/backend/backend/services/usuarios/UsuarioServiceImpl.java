package com.backend.backend.services.usuarios;

import com.backend.backend.dto.UsuarioDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.models.usuarios.Usuario;
import com.backend.backend.repository.EmpleadoRepository;
import com.backend.backend.repository.UsuarioRepository;
import com.backend.backend.utils.MapperUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final EmpleadoRepository empleadoRepository;

    private final MapperUtil mapperUtil;

    private final PasswordEncoder passwordEncoder;

    Logger logger = Logger.getLogger(UsuarioServiceImpl.class.getName());

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, MapperUtil mapperUtil,
                              EmpleadoRepository empleadoRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.mapperUtil = mapperUtil;
        this.empleadoRepository = empleadoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario getUsuarioById(Long id) {
        logger.log(Level.INFO, "Buscando usuario por id: {}", id);
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public UsuarioDTO getUsuarioDTOById(Long id) {
        logger.log(Level.INFO, "Buscando usuarioDTO por id: {}", id);
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario == null) return null;

        return mapperUtil.mapUsuarioToUsuarioDTO(usuario);
    }

    @Override
    public Usuario getUsuarioByEmail(String email) {
        logger.log(Level.INFO, "Buscando usuario por email: {}", email);
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario createUsuario(Usuario usuario) throws BusinessException {
        logger.info("Creando usuario: " + usuario.getEmail());

        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            logger.warning("El email ya está en uso");
            throw new BusinessException("El email ya está en uso");
        }

        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    @Override
    public void updateUsuario(Long id, Usuario usuario, Long idResponsable) throws BusinessException {
        Usuario usuarioToUpdate = this.getUsuarioById(id);
        if (usuarioToUpdate == null) {
            logger.warning("El usuario no existe");
            throw new BusinessException("El usuario no existe");
        }

        // Si hay cambios en los datos del usuario comprobamos que tenga permisos
        if (!passwordEncoder.matches(usuario.getPassword(), usuarioToUpdate.getPassword())
            || !usuario.getRol().equals(usuarioToUpdate.getRol())) {

            Usuario responsable = this.getUsuarioById(idResponsable);
            if (responsable == null) {
                logger.warning("El usuario responsable no existe");
                throw new BusinessException("El usuario responsable no existe");
            }

            if (!responsable.getAccionesPermitidas().contains("EDICION_INFORMACION_USUARIOS")) {
                logger.warning("El usuario responsable no tiene permisos para editar usuarios");
                throw new BusinessException("El usuario responsable no tiene permisos para editar usuarios");
            }

            if (!usuario.getEmail().equals(usuarioToUpdate.getEmail())) {
                logger.warning("No se puede cambiar el email de un usuario");
                throw new BusinessException("No se puede cambiar el email de un usuario");
            }

            usuarioToUpdate.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioToUpdate.setRol(usuario.getRol());
            usuarioRepository.save(usuarioToUpdate);
        }
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
        return empleadoRepository.findByUsuarioIdAndIdEmpresa(idUsuario, idEmpresa) == null;
    }

}
