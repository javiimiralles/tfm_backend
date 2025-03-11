package com.backend.backend.enums;

import lombok.Getter;

@Getter
public enum MetodoPagoEnum {

    EFECTIVO("Efectivo"),
    TARJETA("Tarjeta"),
    TRANSFERENCIA("Transferencia"),
    PAYPAL("Paypal");

    private final String metodoPago;

    MetodoPagoEnum(String metodoPago) {
        this.metodoPago = metodoPago;
    }

}
