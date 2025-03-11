package com.backend.backend.enums;

import lombok.Getter;

@Getter
public enum RutasCloudinaryEnum {

    PATH_IMAGEN_PRODUCTOS("core/products"),
    PATH_FOTO_PERFIL_EMPLEADOS("core/employees");

    private final String ruta;

    RutasCloudinaryEnum(String ruta) {
        this.ruta = ruta;
    }

}
