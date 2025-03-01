package com.backend.backend.services.proveedores;

import com.backend.backend.dto.DatosPedidoProveedor;
import com.backend.backend.enums.EstadoPedidoProveedorEnum;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.models.proveedores.DetallePedidoProveedor;
import com.backend.backend.models.proveedores.PedidoProveedor;
import com.backend.backend.repository.DetallePedidoProveedorRepository;
import com.backend.backend.repository.PedidoProveedorRepository;
import com.backend.backend.services.inventario.ProductoService;
import com.backend.backend.services.usuarios.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class PedidoProveedorServiceImpl implements PedidoProveedorService {

    private final PedidoProveedorRepository pedidoProveedorRepository;

    private final DetallePedidoProveedorRepository detallePedidoProveedorRepository;

    private final UsuarioService usuarioService;

    private final ProductoService productoService;

    Logger logger = LoggerFactory.getLogger(PedidoProveedorServiceImpl.class);

    public PedidoProveedorServiceImpl(PedidoProveedorRepository pedidoProveedorRepository, UsuarioService usuarioService,
                                      ProductoService productoService, DetallePedidoProveedorRepository detallePedidoProveedorRepository) {
        this.pedidoProveedorRepository = pedidoProveedorRepository;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
        this.detallePedidoProveedorRepository = detallePedidoProveedorRepository;
    }

    @Transactional
    @Override
    public void realizarPedidoProveedor(DatosPedidoProveedor datosPedidoProveedor, Long idResponsable) {
        logger.info("Realizando pedido a proveedor");

        if (!usuarioService.validateUsuarioResponsable(idResponsable, datosPedidoProveedor.getIdEmpresa())) {
            throw new BusinessException("El usuario responsable no existe o no pertenece a la empresa");
        }

        BigDecimal costeTotal = BigDecimal.ZERO;
        for (DetallePedidoProveedor detalle : datosPedidoProveedor.getDetallesPedido()) {
            BigDecimal costeUnitario = productoService.getCosteProducto(detalle.getIdProducto());
            detalle.setPrecioUnitario(costeUnitario);
            detalle.setSubtotal(costeUnitario.multiply(BigDecimal.valueOf(detalle.getCantidad())));
            costeTotal = costeTotal.add(detalle.getSubtotal());
        }

        PedidoProveedor pedido = new PedidoProveedor();
        pedido.setIdProveedor(datosPedidoProveedor.getIdProveedor());
        pedido.setIdEmpresa(datosPedidoProveedor.getIdEmpresa());
        pedido.setFechaPedido(new Date());
        pedido.setEstado(EstadoPedidoProveedorEnum.PENDIENTE);
        pedido.setCosteTotal(costeTotal);
        PedidoProveedor pedidoCreado = pedidoProveedorRepository.save(pedido);

        for (DetallePedidoProveedor detalle : datosPedidoProveedor.getDetallesPedido()) {
            detalle.setIdPedidoProveedor(pedidoCreado.getId());
            detallePedidoProveedorRepository.save(detalle);
        }
    }
}
