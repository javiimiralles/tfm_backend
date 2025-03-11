package com.backend.backend.enums;

import lombok.Getter;

@Getter
public enum GeneroEnum {

    HOMBRE("Hombre"),
    MUJER("Mujer"),
    OTRO("Otro");

    private final String genero;

    GeneroEnum(String genero){
        this.genero = genero;
    }

}
