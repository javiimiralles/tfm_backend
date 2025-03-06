package com.backend.backend.services.proveedores;

import com.backend.backend.models.proveedores.DetallePedidoProveedor;
import com.backend.backend.repository.DetallePedidoProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DetallePedidoProveedorServiceImpl implements DetallePedidoProveedorService {

    private final DetallePedidoProveedorRepository detallePedidoProveedorRepository;

    Logger logger = Logger.getLogger(DetallePedidoProveedorServiceImpl.class.getName());

    public DetallePedidoProveedorServiceImpl(DetallePedidoProveedorRepository detallePedidoProveedorRepository) {
        this.detallePedidoProveedorRepository = detallePedidoProveedorRepository;
    }

    @Override
    public List<DetallePedidoProveedor> findDetallesPedidoProveedorByIdPedidoProveedor(Long idPedidoProveedor) {
        logger.log(Level.INFO, "Buscando detalles de pedido proveedor con id: {0}", idPedidoProveedor);
        return detallePedidoProveedorRepository.findByIdPedidoProveedor(idPedidoProveedor);
    }

    @Transactional
    @Override
    public void createDetallePedidoProveedor(DetallePedidoProveedor detallePedidoProveedor) {
        logger.log(Level.INFO, "Creando detalle de pedido proveedor: {0}", detallePedidoProveedor);
        detallePedidoProveedorRepository.save(detallePedidoProveedor);
    }
}
