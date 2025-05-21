package com.backend.backend.enums;

import lombok.Getter;

@Getter
public enum EstadoPedidoProveedorEnum {

    PENDIENTE("Pendiente"),
    ENVIADO("Enviado"),
    RECIBIDO("Recibido"),
    CANCELADO("Cancelado");

    private final String estadoPedidoProveedor;

    EstadoPedidoProveedorEnum(String estadoPedidoProveedor){
        this.estadoPedidoProveedor = estadoPedidoProveedor;
    }

    public static EstadoPedidoProveedorEnum getEstadoPedidoProveedorEnum(String estadoPedidoProveedor){
        for(EstadoPedidoProveedorEnum estado : EstadoPedidoProveedorEnum.values()){
            if(estado.getEstadoPedidoProveedor().equals(estadoPedidoProveedor)){
                return estado;
            }
        }
        return null;
    }

    public static int getEstadoPedidoProveedorEnumId(EstadoPedidoProveedorEnum estadoPedidoProveedor){
        for(EstadoPedidoProveedorEnum estado : EstadoPedidoProveedorEnum.values()){
            if(estado.equals(estadoPedidoProveedor)){
                return estado.ordinal();
            }
        }
        return -1;

    }
}
