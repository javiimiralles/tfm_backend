package com.backend.backend.enums;

public enum RutasCloudinaryEnum {

    PATH_IMAGEN_PRODUCTOS("core/products"),
    PATH_FOTO_PERFIL_EMPLEADOS("core/employees");

    private final String ruta;

    RutasCloudinaryEnum(String ruta) {
        this.ruta = ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
