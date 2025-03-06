package com.backend.backend.services.proveedores;

import com.backend.backend.dto.DatosPedidoProveedor;
import com.backend.backend.enums.EstadoPedidoProveedorEnum;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.PedidoProveedorFilter;
import com.backend.backend.models.proveedores.DetallePedidoProveedor;
import com.backend.backend.models.proveedores.PedidoProveedor;
import com.backend.backend.repository.PedidoProveedorRepository;
import com.backend.backend.services.inventario.ProductoService;
import com.backend.backend.services.usuarios.UsuarioService;
import com.backend.backend.specifications.PedidoProveedorSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PedidoProveedorServiceImpl implements PedidoProveedorService {

    private final PedidoProveedorRepository pedidoProveedorRepository;

    private final DetallePedidoProveedorService detallePedidoProveedorService;

    private final UsuarioService usuarioService;

    private final ProductoService productoService;

    private final ProveedorService proveedorService;

    Logger logger = Logger.getLogger(PedidoProveedorServiceImpl.class.getName());

    public PedidoProveedorServiceImpl(PedidoProveedorRepository pedidoProveedorRepository, UsuarioService usuarioService,
                                      ProductoService productoService, DetallePedidoProveedorService detallePedidoProveedorService,
                                      ProveedorService proveedorService) {
        this.pedidoProveedorRepository = pedidoProveedorRepository;
        this.usuarioService = usuarioService;
        this.productoService = productoService;
        this.detallePedidoProveedorService = detallePedidoProveedorService;
        this.proveedorService = proveedorService;
    }

    @Override
    public PedidoProveedor getPedidoProveedorById(Long id) {
        logger.log(Level.INFO, "Buscando pedido proveedor con id: {0}", id);
        return pedidoProveedorRepository.findById(id).orElse(null);
    }

    @Override
    public Page<PedidoProveedor> findPedidosProveedorByFilter(PedidoProveedorFilter filter) throws BusinessException {
        logger.log(Level.INFO, "Buscando pedidos proveedor con filtro: {0}", filter);

        if (filter.getIdEmpresa() == null) {
            logger.log(Level.WARNING, "El idEmpresa es obligatorio");
            throw new BusinessException("El idEmpresa es obligatorio");
        }

        Specification<PedidoProveedor> spec = PedidoProveedorSpecification.withFilters(filter);
        Pageable pageable = filter.getPageable();

        return pedidoProveedorRepository.findAll(spec, pageable);
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
            BigDecimal costeUnitario = productoService.getCosteProducto(detalle.getProducto().getId());
            detalle.setPrecioUnitario(costeUnitario);
            detalle.setSubtotal(costeUnitario.multiply(BigDecimal.valueOf(detalle.getCantidad())));
            costeTotal = costeTotal.add(detalle.getSubtotal());
        }

        PedidoProveedor pedido = new PedidoProveedor();
        pedido.setProveedor(proveedorService.getProveedorById(datosPedidoProveedor.getIdProveedor()));
        pedido.setIdEmpresa(datosPedidoProveedor.getIdEmpresa());
        pedido.setFechaPedido(new Date());
        pedido.setEstado(EstadoPedidoProveedorEnum.PENDIENTE);
        pedido.setCosteTotal(costeTotal);
        PedidoProveedor pedidoCreado = pedidoProveedorRepository.save(pedido);

        for (DetallePedidoProveedor detalle : datosPedidoProveedor.getDetallesPedido()) {
            detalle.setIdPedidoProveedor(pedidoCreado.getId());
            detallePedidoProveedorService.createDetallePedidoProveedor(detalle);
        }
    }

    @Transactional
    @Override
    public void updateEstadoPedidoProveedor(Long idPedido, EstadoPedidoProveedorEnum nuevoEstado) {
        logger.log(Level.INFO, "Actualizando estado del pedido proveedor con id: {0}", idPedido);
        PedidoProveedor pedido = pedidoProveedorRepository.findById(idPedido).orElse(null);
        if (pedido == null) {
            logger.log(Level.WARNING, "El pedido proveedor con id {0} no existe", idPedido);
            throw new BusinessException("El pedido proveedor no existe");
        }
        if (nuevoEstado.equals(EstadoPedidoProveedorEnum.RECIBIDO)) {
            List<DetallePedidoProveedor> detalles = detallePedidoProveedorService.findDetallesPedidoProveedorByIdPedidoProveedor(idPedido);
            for (DetallePedidoProveedor detalle : detalles) {
                productoService.updateStockProducto(detalle.getProducto().getId(), detalle.getCantidad());
            }
        }
        pedido.setEstado(nuevoEstado);
        pedidoProveedorRepository.save(pedido);
    }
}
