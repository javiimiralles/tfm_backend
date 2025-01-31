package com.backend.backend.models;

import com.backend.backend.enums.EstadoPedidoProveedorEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "pedidos_proveedores")
public class PedidoProveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_proveedor", nullable = false)
    private Long idProveedor;

    @Column(name = "id_empresa", nullable = false)
    private Long idEmpresa;

    @Column(name = "fecha_pedido", nullable = false)
    private Date fechaPedido;

    @Column(name = "estado", nullable = false)
    private EstadoPedidoProveedorEnum estado;

    @Column(name = "coste_total", nullable = false)
    private Double costeTotal;
}
