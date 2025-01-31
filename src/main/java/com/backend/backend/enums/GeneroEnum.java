package com.backend.backend.enums;

public enum GeneroEnum {

    HOMBRE("Hombre"),
    MUJER("Mujer"),
    OTRO("Otro");

    private final String genero;

    GeneroEnum(String genero){
        this.genero = genero;
    }

    public String getGenero(){
        return genero;
    }
}
