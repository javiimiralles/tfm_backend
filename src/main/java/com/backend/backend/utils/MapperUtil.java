package com.backend.backend.utils;

import com.backend.backend.dto.ClienteDTO;
import com.backend.backend.dto.EmpleadoDTO;
import com.backend.backend.dto.UsuarioDTO;
import com.backend.backend.models.Cliente;
import com.backend.backend.models.Empleado;
import com.backend.backend.models.Usuario;
import org.springframework.stereotype.Component;

@Component
public class MapperUtil {

    public UsuarioDTO mapUsuarioToUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setEmail(usuario.getEmail());
        usuarioDTO.setRol(usuario.getRol().getNombre());
        usuarioDTO.setPermisos(usuario.getAccionesPermitidas());
        return usuarioDTO;
    }

    public ClienteDTO mapClienteToClienteDTO(Cliente cliente) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellidos(cliente.getApellidos());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setFechaAlta(cliente.getFechaAlta());
        return clienteDTO;
    }

    public EmpleadoDTO mapEmpleadoToEmpleadoDTO(Empleado empleado) {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setId(empleado.getId());
        empleadoDTO.setIdEmpresa(empleado.getIdEmpresa());
        empleadoDTO.setNombre(empleado.getNombre());
        empleadoDTO.setApellidos(empleado.getApellidos());
        empleadoDTO.setRol(empleado.getUsuario().getRol().getNombre());
        empleadoDTO.setFechaAlta(empleado.getFechaAlta());
        empleadoDTO.setFechaBaja(empleado.getFechaBaja());
        return empleadoDTO;
    }
}
