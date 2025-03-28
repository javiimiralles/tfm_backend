package com.backend.backend.services.ventas;

import com.backend.backend.models.ventas.DetallePedido;

import java.util.List;

public interface DetallePedidoService {
    List<DetallePedido> findDetallesPedidoByIdPedido(Long idPedido);
}
