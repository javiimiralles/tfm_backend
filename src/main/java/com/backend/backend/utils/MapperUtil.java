package com.backend.backend.utils;

import com.backend.backend.dto.*;
import com.backend.backend.models.clientes.Cliente;
import com.backend.backend.models.empleados.Empleado;
import com.backend.backend.models.inventario.Producto;
import com.backend.backend.models.proveedores.Proveedor;
import com.backend.backend.models.usuarios.Usuario;
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

    public ProductoDTO mapProductoToProductoDTO(Producto producto) {
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setId(producto.getId());
        productoDTO.setCategoria(producto.getCategoria() != null ? producto.getCategoria().getNombre() : null);
        productoDTO.setIdEmpresa(producto.getIdEmpresa());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setImagenUrl(producto.getImagenUrl());
        productoDTO.setPrecioVenta(producto.getPrecioVenta());
        productoDTO.setStock(producto.getStock());
        productoDTO.setFechaBaja(producto.getFechaBaja());
        return productoDTO;
    }

    public ProveedorDTO mapProveedorToProveedorDTO(Proveedor proveedor) {
        ProveedorDTO proveedorDTO = new ProveedorDTO();
        proveedorDTO.setId(proveedor.getId());
        proveedorDTO.setIdEmpresa(proveedor.getIdEmpresa());
        proveedorDTO.setNombre(proveedor.getNombre());
        proveedorDTO.setEmail(proveedor.getEmail());
        proveedorDTO.setFechaAlta(proveedor.getFechaAlta());
        return proveedorDTO;
    }
}
