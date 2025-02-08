package com.backend.backend.dto;

import com.backend.backend.enums.GeneroEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class RegistroEmpleadoForm {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 150, message = "El nombre no puede tener más de 150 caracteres")
    private String nombreEmpleado;

    @Size(max = 150, message = "Los apellidos no pueden tener más de 150 caracteres")
    private String apellidosEmpleado;

    @Size(max = 15, message = "El teléfono no puede tener más de 15 caracteres")
    private String telefonoEmpleado;

    @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
    private String direccionEmpleado;

    private Long idPaisEmpleado;

    @Size(max = 100, message = "La provincia no puede tener más de 100 caracteres")
    private String provinciaEmpleado;

    @Size(max = 100, message = "La población no puede tener más de 100 caracteres")
    private String poblacionEmpleado;

    @Size(max = 10, message = "El código postal no puede tener más de 10 caracteres")
    private String codigoPostalEmpleado;

    private Date fechaNacimientoEmpleado;

    private GeneroEnum generoEmpleado;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email no es válido")
    @Size(max = 150, message = "El email no puede tener más de 150 caracteres")
    private String emailUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    private String passwordUsuario;

    @NotBlank(message = "El nombre de la empresa es obligatorio")
    @Size(max = 150, message = "El nombre de la empresa no puede tener más de 150 caracteres")
    private String nombreEmpresa;

    @NotBlank(message = "La razón social es obligatoria")
    @Size(max = 50, message = "La razón social no puede tener más de 50 caracteres")
    private String razonSocialEmpresa;

    @Size(max = 255, message = "La dirección de la empresa no puede tener más de 255 caracteres")
    private String direccionEmpresa;

    @Size(max = 15, message = "El teléfono de la empresa no puede tener más de 15 caracteres")
    private String telefonoEmpresa;

    @NotBlank(message = "El email de la empresa es obligatorio")
    @Email(message = "El email de la empresa no es válido")
    @Size(max = 150, message = "El email de la empresa no puede tener más de 150 caracteres")
    private String emailEmpresa;
}
