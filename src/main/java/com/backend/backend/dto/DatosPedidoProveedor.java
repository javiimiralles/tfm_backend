package com.backend.backend.dto;

import com.backend.backend.models.proveedores.DetallePedidoProveedor;
import lombok.Data;

import java.util.List;

@Data
public class DatosPedidoProveedor {

    private Long idEmpresa;

    private Long idProveedor;

    private List<DetallePedidoProveedor> detallesPedido;
}
