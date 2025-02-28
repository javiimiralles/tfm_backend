package com.backend.backend.dto;

import com.backend.backend.enums.GeneroEnum;
import com.backend.backend.models.paises.Pais;
import com.backend.backend.models.roles.Rol;
import lombok.Data;

import java.util.Date;

@Data
public class EmpleadoForm {

    private Long id;

    private Long idEmpresa;

    private Long idUsuario;

    private String nombre;

    private String apellidos;

    private String nif;

    private String telefono;

    private String direccion;

    private Date fechaNacimiento;

    private GeneroEnum genero;

    private Pais pais;

    private String provincia;

    private String poblacion;

    private String codigoPostal;

    // Datos de usuario

    private String email;

    private String password;

    private Rol rol;
}
