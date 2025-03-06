package com.backend.backend.services.proveedores;

import com.backend.backend.models.proveedores.DetallePedidoProveedor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DetallePedidoProveedorService {
    List<DetallePedidoProveedor> findDetallesPedidoProveedorByIdPedidoProveedor(Long idPedidoProveedor);

    @Transactional
    void createDetallePedidoProveedor(DetallePedidoProveedor detallePedidoProveedor);
}
