package com.backend.backend.dto;

import com.backend.backend.enums.MetodoPagoEnum;
import com.backend.backend.models.ventas.DetallePedido;
import lombok.Data;

import java.util.List;

@Data
public class DatosPedido {

    private Long idEmpresa;

    private Long idCliente;

    private MetodoPagoEnum metodoPago;

    private List<DetallePedido> detallesPedido;
}
