package com.backend.backend.enums;

public enum MetodoPagoEnum {

    EFECTIVO("Efectivo"),
    TARJETA("Tarjeta"),
    TRANSFERENCIA("Transferencia"),
    PAYPAL("Paypal");

    private final String metodoPago;

    MetodoPagoEnum(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }
}
