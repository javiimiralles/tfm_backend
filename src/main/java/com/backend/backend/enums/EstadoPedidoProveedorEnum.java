package com.backend.backend.enums;

public enum EstadoPedidoProveedorEnum {

    PENDIENTE("Pendiente"),
    ENVIADO("Enviado"),
    RECIBIDO("Recibido"),
    CANCELADO("Cancelado");

    private final String estadoPedidoProveedor;

    EstadoPedidoProveedorEnum(String estadoPedidoProveedor){
        this.estadoPedidoProveedor = estadoPedidoProveedor;
    }

    public String getEstadoPedidoProveedor(){
        return estadoPedidoProveedor;
    }
}
