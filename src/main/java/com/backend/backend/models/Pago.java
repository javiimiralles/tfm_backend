package com.backend.backend.models;

import com.backend.backend.enums.MetodoPagoEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_pedido", nullable = false)
    private Long idPedido;

    @Column(name = "id_empresa", nullable = false)
    private Long idEmpresa;

    @Column(name = "fecha_pago", nullable = false)
    private Date fechaPago;

    @Column(name = "importe", nullable = false)
    private Double importe;

    @Column(name = "metodo_pago", nullable = false)
    private MetodoPagoEnum metodoPago;
}
