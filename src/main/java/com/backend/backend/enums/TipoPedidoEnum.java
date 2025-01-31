package com.backend.backend.enums;

public enum TipoPedidoEnum {

    PEDIDO("Pedido"),
    PRESUPUESTO("Presupuesto");

    private String tipoPedido;

    TipoPedidoEnum(String tipoPedido) {
        this.tipoPedido = tipoPedido;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }
}
