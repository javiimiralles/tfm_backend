package com.backend.backend.services.proveedores;

import com.backend.backend.dto.DatosPedidoProveedor;
import com.backend.backend.enums.EstadoPedidoProveedorEnum;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.PedidoProveedorFilter;
import com.backend.backend.models.proveedores.PedidoProveedor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface PedidoProveedorService {
    PedidoProveedor getPedidoProveedorById(Long id);

    Page<PedidoProveedor> findPedidosProveedorByFilter(PedidoProveedorFilter filter) throws BusinessException;

    @Transactional
    void realizarPedidoProveedor(DatosPedidoProveedor datosPedidoProveedor, Long idResponsable);

    @Transactional
    void updateEstadoPedidoProveedor(Long idPedido, EstadoPedidoProveedorEnum nuevoEstado);
}
