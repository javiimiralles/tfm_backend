package com.backend.backend.services.empleados;

import com.backend.backend.dto.EmpleadoDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.EmpleadoFilter;
import com.backend.backend.models.empleados.Empleado;
import com.backend.backend.models.usuarios.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EmpleadoService {

    Empleado getEmpleadoById(Long id);

    Page<EmpleadoDTO> findEmpleadosDTOByFilter(EmpleadoFilter filter) throws BusinessException;

    Empleado getEmpleadoByIdUsuario(Long idUsuario);

    Empleado getEmpleadoByIdUsuarioAndIdEmpresa(Long idUsuario, Long idEmpresa);

    @Transactional
    void createEmpleado(Empleado empleado, Usuario usuario, MultipartFile imagen, Long idResponsable) throws IOException;

    @Transactional
    void updateEmpleado(Long id, Empleado empleado, Usuario usuario, MultipartFile imagen, boolean imageChanged, Long idResponsable) throws IOException;
}
