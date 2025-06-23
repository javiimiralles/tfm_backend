package com.backend.backend.services.roles;

import com.backend.backend.models.roles.Accion;

import java.util.List;

public interface AccionService {
    List<Accion> getAcciones();

    Accion createAccion(Accion accion);
}
