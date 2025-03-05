package com.backend.backend.models.proveedores;

import com.backend.backend.enums.EstadoPedidoProveedorEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "pedidos_proveedores")
public class PedidoProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_proveedor", referencedColumnName = "id", nullable = false)
    private Proveedor proveedor;

    @Column(name = "id_empresa", nullable = false)
    private Long idEmpresa;

    @Column(name = "fecha_pedido", nullable = false)
    private Date fechaPedido;

    @Column(name = "estado", nullable = false)
    private EstadoPedidoProveedorEnum estado;

    @Column(name = "coste_total", nullable = false)
    private BigDecimal costeTotal;
}
