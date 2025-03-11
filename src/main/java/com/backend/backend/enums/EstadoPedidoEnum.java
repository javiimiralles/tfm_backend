package com.backend.backend.enums;

import lombok.Getter;

@Getter
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

    public static EstadoPedidoEnum getEstadoPedidoEnum(String estado) {
        for (EstadoPedidoEnum estadoPedidoEnum : EstadoPedidoEnum.values()) {
            if (estadoPedidoEnum.getEstado().equals(estado)) {
                return estadoPedidoEnum;
            }
        }
        return null;
    }

}
