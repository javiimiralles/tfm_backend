package com.backend.backend.services.ventas;

import com.backend.backend.models.ventas.DetallePedido;
import com.backend.backend.repository.DetallePedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;

    Logger logger = Logger.getLogger(DetallePedidoServiceImpl.class.getName());

    public DetallePedidoServiceImpl(DetallePedidoRepository detallePedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Override
    public List<DetallePedido> findDetallesPedidoByIdPedido(Long idPedido) {
        logger.log(Level.INFO, "Buscando detalles de pedido con id: {0}", idPedido);
        return detallePedidoRepository.findByIdPedido(idPedido);
    }
}
