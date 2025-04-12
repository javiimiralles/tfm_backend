package com.backend.backend.models.ventas;

import com.backend.backend.models.clientes.Cliente;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_pedido", referencedColumnName = "id", nullable = false)
    private Pedido pedido;

    @Column(name = "id_empresa", nullable = false)
    private Long idEmpresa;

    @ManyToOne
    @JoinColumn(name = "id_cliente", referencedColumnName = "id", nullable = false)
    private Cliente cliente;

    @Column(name = "fecha_factura", nullable = false)
    private Date fechaFactura;

    @Column(name = "fecha_vencimiento", nullable = false)
    private Date fechaVencimiento;

    @Column(name = "importe", nullable = false)
    private BigDecimal importe;

    @Column(name = "numero_factura", length = 50, nullable = false)
    private String numeroFactura;
}
