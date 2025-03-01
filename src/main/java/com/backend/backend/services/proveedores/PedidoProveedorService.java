package com.backend.backend.services.proveedores;

import com.backend.backend.dto.DatosPedidoProveedor;
import org.springframework.transaction.annotation.Transactional;

public interface PedidoProveedorService {
    @Transactional
    void realizarPedidoProveedor(DatosPedidoProveedor datosPedidoProveedor, Long idResponsable);
}
