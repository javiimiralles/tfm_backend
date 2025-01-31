package com.backend.backend.models;

import com.backend.backend.enums.EstadoPedidoEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "historial_pedidos")
public class HistorialPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pedido", nullable = false)
    private Long idPedido;

    @Column(name = "estado_anterior", nullable = false)
    private EstadoPedidoEnum estadoAnterior;

    @Column(name = "estado_nuevo", nullable = false)
    private EstadoPedidoEnum estadoNuevo;

    @Column(name = "id_resp_modif", nullable = false)
    private Long idRespModif;

    @Column(name = "fecha_modif", nullable = false)
    private Date fechaModif;
}
