package com.backend.backend.services.empleados;

import com.backend.backend.dto.EmpleadoDTO;
import com.backend.backend.enums.RutasCloudinaryEnum;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.EmpleadoFilter;
import com.backend.backend.models.empleados.Empleado;
import com.backend.backend.models.usuarios.Usuario;
import com.backend.backend.repository.EmpleadoRepository;
import com.backend.backend.services.cloudinary.CloudinaryService;
import com.backend.backend.services.usuarios.UsuarioService;
import com.backend.backend.specifications.EmpleadoSpecification;
import com.backend.backend.utils.MapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    private final MapperUtil mapperUtil;

    private final UsuarioService usuarioService;

    private final CloudinaryService cloudinaryService;

    Logger logger = Logger.getLogger(EmpleadoServiceImpl.class.getName());

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository, MapperUtil mapperUtil,
                                UsuarioService usuarioService, CloudinaryService cloudinaryService) {
        this.empleadoRepository = empleadoRepository;
        this.mapperUtil = mapperUtil;
        this.usuarioService = usuarioService;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
    public Empleado getEmpleadoById(Long id) {
        logger.log(Level.INFO, "Obteniendo empleado por id: {}", id);
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public Page<EmpleadoDTO> findEmpleadosDTOByFilter(EmpleadoFilter filter) throws BusinessException {
        logger.log(Level.INFO, "Obteniendo empleados por filtro: {}", filter);
        if (filter.getIdEmpresa() == null) {
            logger.log(Level.WARNING, "El idEmpresa es obligatorio");
            throw new BusinessException("El idEmpresa es obligatorio");
        }
        Specification<Empleado> spec = EmpleadoSpecification.withFilters(filter);
        Pageable pageable = filter.getPageable();

        Page<Empleado> empleados = empleadoRepository.findAll(spec, pageable);
        return empleados.map(mapperUtil::mapEmpleadoToEmpleadoDTO);
    }

    @Override
    public Empleado getEmpleadoByIdUsuario(Long idUsuario) {
        logger.log(Level.INFO, "Obteniendo empleado por idUsuario: {}", idUsuario);
        return empleadoRepository.findByUsuarioId(idUsuario);
    }

    @Override
    public Empleado getEmpleadoByIdUsuarioAndIdEmpresa(Long idUsuario, Long idEmpresa) {
        logger.log(Level.INFO, "Obteniendo empleado por idUsuario: {} y idEmpresa: {}", new Object[]{idUsuario, idEmpresa});
        return empleadoRepository.findByUsuarioIdAndIdEmpresa(idUsuario, idEmpresa);
    }

    @Transactional
    @Override
    public void createEmpleado(Empleado empleado, Usuario usuario, MultipartFile imagen, Long idResponsable) throws IOException {
        logger.info("Creando empleado: " + empleado.getNombre());

        // Creamos el usuario al que se asociara el empleado
        usuario = usuarioService.createUsuario(usuario);
        empleado.setUsuario(usuario);

        // Si el idResponsable es null quiere decir que se ha llamado desde register y el responsable es el propio usuario
        if (idResponsable == null) idResponsable = usuario.getId();

        if (imagen != null && !imagen.isEmpty()) {
            String url = cloudinaryService.uploadImage(imagen, RutasCloudinaryEnum.PATH_FOTO_PERFIL_EMPLEADOS.getRuta());
            empleado.setImagenUrl(url);
        }

        empleado.setIdRespAlta(idResponsable);
        empleado.setFechaAlta(new Date());
        empleadoRepository.save(empleado);
    }

    @Transactional
    @Override
    public void updateEmpleado(Long id, Empleado empleado, Usuario usuario, MultipartFile imagen, boolean imageChanged, Long idResponsable) throws IOException {
        logger.info("Actualizando empleado: " + empleado.getNombre());

        Empleado empleadoToUpdate = empleadoRepository.findById(id).orElse(null);
        if (empleadoToUpdate == null) {
            throw new BusinessException("El empleado no existe");
        }

        // En el caso de que sea necesario se actualiza el usuario
        if (usuario != null) {
            usuarioService.updateUsuario(empleadoToUpdate.getUsuario().getId(), usuario, idResponsable);
        }

        if (imageChanged) {
            if (imagen != null && !imagen.isEmpty()) {
                String url = cloudinaryService.uploadImage(imagen, RutasCloudinaryEnum.PATH_FOTO_PERFIL_EMPLEADOS.getRuta());
                empleado.setImagenUrl(url);
            } else {
                empleado.setImagenUrl(null);
            }
        }

        empleado.setIdRespModif(idResponsable);
        empleado.setFechaModif(new Date());
        empleadoRepository.save(empleado);
    }
}
