package com.backend.backend.services.ventas;

import com.backend.backend.dto.DatosPedido;
import com.backend.backend.enums.EstadoPedidoEnum;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.PedidoFilter;
import com.backend.backend.models.ventas.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

public interface PedidoService {
    Pedido getPedidoById(Long id);

    Page<Pedido> findPedidosByFilter(PedidoFilter filter) throws BusinessException;

    @Transactional
    void createPresupuesto(DatosPedido datosPedido, Long idResponsable);

    @Transactional
    void updatePresupuesto(Long idPedido, DatosPedido datosPedido, Long idResponsable);

    @Transactional
    void aceptarPresupuesto(Long idPedido, Long idResponsable);

    @Transactional
    void cancelarPresupuesto(Long idPedido, Long idResponsable);

    @Transactional
    void createPedido(DatosPedido datosPedido, Long idResponsable);

    @Transactional
    void updateEstadoPedido(Long idPedido, EstadoPedidoEnum nuevoEstado, Long idResponsable);
}
