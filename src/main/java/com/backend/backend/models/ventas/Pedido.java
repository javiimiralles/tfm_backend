package com.backend.backend.models.ventas;

import com.backend.backend.enums.EstadoPedidoEnum;
import com.backend.backend.enums.MetodoPagoEnum;
import com.backend.backend.enums.TipoPedidoEnum;
import com.backend.backend.models.clientes.Cliente;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id", nullable = false)
    private Cliente cliente;

    @Column(name = "id_empresa", nullable = false)
    private Long idEmpresa;

    @Column(name = "fecha_pedido", nullable = false)
    private Date fechaPedido;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "estado", nullable = false)
    private EstadoPedidoEnum estado;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo", nullable = false)
    private TipoPedidoEnum tipo;

    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPagoEnum metodoPago;

    @Column(name = "coste_total", nullable = false)
    private BigDecimal costeTotal;

    @Column(name = "observaciones", length = 1000)
    private String observaciones;
}
