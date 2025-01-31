package com.backend.backend.enums;

public enum EstadoPedidoEnum {

    PENDIENTE("Pendiente"),
    PROCESADO("Procesado"),
    ENVIADO("Enviado"),
    RECIBIDO("Recibido"),
    CANCELADO("Cancelado");

    private final String estado;

    EstadoPedidoEnum(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
}
