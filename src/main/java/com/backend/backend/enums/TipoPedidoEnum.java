package com.backend.backend.enums;

import lombok.Getter;

@Getter
public enum TipoPedidoEnum {

    PEDIDO("Pedido"),
    PRESUPUESTO("Presupuesto");

    private String tipoPedido;

    TipoPedidoEnum(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

}
