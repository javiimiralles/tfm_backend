package com.backend.backend.services.ventas;

import com.backend.backend.dto.DatosPedido;
import com.backend.backend.enums.EstadoPedidoEnum;
import com.backend.backend.enums.TipoPedidoEnum;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.PedidoFilter;
import com.backend.backend.models.ventas.*;
import com.backend.backend.repository.*;
import com.backend.backend.services.clientes.ClienteService;
import com.backend.backend.services.inventario.ProductoService;
import com.backend.backend.services.usuarios.UsuarioService;
import com.backend.backend.specifications.PedidoSpecification;
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
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;

    private final UsuarioService usuarioService;

    private final ProductoService productoService;

    private final ClienteService clienteService;

    private final PagoRepository pagoRepository;

    private final DetallePedidoRepository detallePedidoRepository;

    private final HistorialCompraRepository historialCompraRepository;

    private final HistorialPedidoRepository historialPedidoRepository;

    Logger logger = Logger.getLogger(PedidoServiceImpl.class.getName());

    public PedidoServiceImpl(PedidoRepository pedidoRepository, UsuarioService usuarioService, PagoRepository pagoRepository,
                             HistorialCompraRepository historialCompraRepository, HistorialPedidoRepository historialPedidoRepository,
                             DetallePedidoRepository detallePedidoRepository, ProductoService productoService, ClienteService clienteService) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioService = usuarioService;
        this.pagoRepository = pagoRepository;
        this.historialCompraRepository = historialCompraRepository;
        this.historialPedidoRepository = historialPedidoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
        this.productoService = productoService;
        this.clienteService = clienteService;
    }

    @Override
    public Pedido getPedidoById(Long id) {
        logger.log(Level.INFO, "Obteniendo pedido con id: {}", id);
        return pedidoRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Pedido> findPedidosByFilter(PedidoFilter filter) throws BusinessException {
        logger.log(Level.INFO, "Obteniendo pedidos con filtro: {}", filter);
        if (filter.getIdEmpresa() == null) {
            logger.log(Level.WARNING, "El idEmpresa es obligatorio");
            throw new BusinessException("El idEmpresa es obligatorio");
        }
        Specification<Pedido> spec = PedidoSpecification.withFilters(filter);
        Pageable pageable = filter.getPageable();

        return pedidoRepository.findAll(spec, pageable);
    }

    @Transactional
    @Override
    public void createPresupuesto(DatosPedido datosPedido, Long idResponsable) {
        logger.log(Level.INFO, "Creando presupuesto: {}", datosPedido);

        if (!usuarioService.validateUsuarioResponsable(idResponsable, datosPedido.getIdEmpresa())) {
            throw new BusinessException("El empleado responsable de creación no existe o no pertenece a la empresa");
        }

        BigDecimal costeTotal = calcularCosteTotal(datosPedido.getDetallesPedido());

        Pedido pedido = new Pedido();
        pedido.setIdEmpresa(datosPedido.getIdEmpresa());
        pedido.setCliente(clienteService.getClienteById(datosPedido.getIdCliente()));
        pedido.setObservaciones(datosPedido.getObservaciones());
        pedido.setEstado(EstadoPedidoEnum.PENDIENTE);
        pedido.setTipo(TipoPedidoEnum.PRESUPUESTO);
        pedido.setCosteTotal(costeTotal);
        pedido.setFechaPedido(new Date());
        pedido = pedidoRepository.save(pedido);

        for (DetallePedido detalle : datosPedido.getDetallesPedido()) {
            detalle.setIdPedido(pedido.getId());
            detallePedidoRepository.save(detalle);
        }
    }

    @Transactional
    @Override
    public void updatePresupuesto(Long idPedido, DatosPedido datosPedido, Long idResponsable) {
        logger.log(Level.INFO, "Editando presupuesto: {}", datosPedido);

        if (!usuarioService.validateUsuarioResponsable(idResponsable, datosPedido.getIdEmpresa())) {
            throw new BusinessException("El empleado responsable de creación no existe o no pertenece a la empresa");
        }

        Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);
        if (pedido == null) {
            logger.log(Level.WARNING, "El pedido con id {} no existe", idPedido);
            throw new BusinessException("El pedido no existe");
        }

        if (!pedido.getTipo().equals(TipoPedidoEnum.PRESUPUESTO)) {
            logger.log(Level.WARNING, "El pedido con id {} no es un presupuesto", idPedido);
            throw new BusinessException("No se pueden editar los pedidos");
        }

        BigDecimal costeTotal = calcularCosteTotal(datosPedido.getDetallesPedido());

        pedido.setCliente(clienteService.getClienteById(datosPedido.getIdCliente()));
        pedido.setObservaciones(datosPedido.getObservaciones());
        pedido.setCosteTotal(costeTotal);
        pedido = pedidoRepository.save(pedido);

        List<DetallePedido> existingDetalles = detallePedidoRepository.findByIdPedido(idPedido);
        List<DetallePedido> newDetalles = datosPedido.getDetallesPedido();

        //Actualizar/eliminar los detalles existentes
        for (DetallePedido existingDetalle : existingDetalles) {
            boolean found = false;
            for (DetallePedido newDetalle : newDetalles) {
                if (existingDetalle.getId().equals(newDetalle.getId())) {
                    existingDetalle.setCantidad(newDetalle.getCantidad());
                    existingDetalle.setPrecioUnitario(newDetalle.getPrecioUnitario());
                    existingDetalle.setSubtotal(newDetalle.getSubtotal());
                    detallePedidoRepository.save(existingDetalle);
                    found = true;
                    break;
                }
            }
            if (!found) {
                detallePedidoRepository.delete(existingDetalle);
            }
        }

        // Añadir los nuevos detalles
        for (DetallePedido newDetalle : newDetalles) {
            if (newDetalle.getId() == null) {
                newDetalle.setIdPedido(pedido.getId());
                detallePedidoRepository.save(newDetalle);
            }
        }

    }

    @Transactional
    @Override
    public void aceptarPresupuesto(Long idPedido, Long idResponsable) {
        logger.log(Level.INFO, "Aceptando presupuesto con id: {}", idPedido);

        Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);
        if (pedido == null) {
            logger.log(Level.WARNING, "El pedido con id {} no existe", idPedido);
            throw new BusinessException("El pedido no existe");
        }

        if (!usuarioService.validateUsuarioResponsable(idResponsable, pedido.getIdEmpresa())) {
            throw new BusinessException("El empleado responsable de creación no existe o no pertenece a la empresa");
        }

        pedido.setTipo(TipoPedidoEnum.PEDIDO);
        pedido.setFechaPedido(new Date());
        pedidoRepository.save(pedido);

        HistorialPedido historialPedido = new HistorialPedido();
        historialPedido.setIdPedido(pedido.getId());
        historialPedido.setEstadoNuevo(EstadoPedidoEnum.PENDIENTE);
        historialPedido.setIdRespModif(idResponsable);
        historialPedido.setFechaModif(new Date());
        historialPedidoRepository.save(historialPedido);
    }

    @Transactional
    @Override
    public void cancelarPresupuesto(Long idPedido, Long idResponsable) {
        logger.log(Level.INFO, "Cancelando presupuesto con id: {}", idPedido);

        Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);
        if (pedido == null) {
            logger.log(Level.WARNING, "El pedido con id {} no existe", idPedido);
            throw new BusinessException("El pedido no existe");
        }

        if (!usuarioService.validateUsuarioResponsable(idResponsable, pedido.getIdEmpresa())) {
            throw new BusinessException("El empleado responsable de creación no existe o no pertenece a la empresa");
        }

        pedido.setEstado(EstadoPedidoEnum.CANCELADO);
        pedidoRepository.save(pedido);
    }

    @Transactional
    @Override
    public void createPedido(DatosPedido datosPedido, Long idResponsable) {
        logger.log(Level.INFO, "Creando pedido: {}", datosPedido);

        if (!usuarioService.validateUsuarioResponsable(idResponsable, datosPedido.getIdEmpresa())) {
            throw new BusinessException("El empleado responsable de creación no existe o no pertenece a la empresa");
        }

        BigDecimal costeTotal = calcularCosteTotal(datosPedido.getDetallesPedido());

        Pedido pedido = new Pedido();
        pedido.setIdEmpresa(datosPedido.getIdEmpresa());
        pedido.setCliente(clienteService.getClienteById(datosPedido.getIdCliente()));
        pedido.setObservaciones(datosPedido.getObservaciones());
        pedido.setFechaPedido(new Date());
        pedido.setEstado(EstadoPedidoEnum.PENDIENTE);
        pedido.setTipo(TipoPedidoEnum.PEDIDO);
        pedido.setCosteTotal(costeTotal);
        pedido.setMetodoPago(datosPedido.getMetodoPago());
        Pedido pedidoCreado = pedidoRepository.save(pedido);

        for (DetallePedido detalle : datosPedido.getDetallesPedido()) {
            detalle.setIdPedido(pedidoCreado.getId());
            productoService.updateStockProducto(detalle.getProducto().getId(), detalle.getCantidad()*-1);
            detallePedidoRepository.save(detalle);
        }

        HistorialPedido historialPedido = new HistorialPedido();
        historialPedido.setIdPedido(pedidoCreado.getId());
        historialPedido.setEstadoNuevo(EstadoPedidoEnum.PENDIENTE);
        historialPedido.setIdRespModif(idResponsable);
        historialPedido.setFechaModif(new Date());
        historialPedidoRepository.save(historialPedido);
    }

    @Transactional
    @Override
    public void updateEstadoPedido(Long idPedido, EstadoPedidoEnum nuevoEstado, Long idResponsable) {
        logger.log(Level.INFO, "Actualizando estado del pedido con id: {}", idPedido);

        Pedido pedido = pedidoRepository.findById(idPedido).orElse(null);
        if (pedido == null) {
            logger.log(Level.WARNING, "El pedido con id {} no existe", idPedido);
            throw new BusinessException("El pedido no existe");
        }

        if (!usuarioService.validateUsuarioResponsable(idResponsable, pedido.getIdEmpresa())) {
            throw new BusinessException("El empleado responsable de creación no existe o no pertenece a la empresa");
        }

        HistorialPedido historialPedido = new HistorialPedido();
        historialPedido.setIdPedido(idPedido);
        historialPedido.setEstadoAnterior(pedido.getEstado());
        historialPedido.setEstadoNuevo(nuevoEstado);
        historialPedido.setIdRespModif(idResponsable);
        historialPedido.setFechaModif(new Date());
        historialPedidoRepository.save(historialPedido);

        // Si el pedido se ha recibido, se crea un pago y se guarda en el historial de compras
        if (nuevoEstado.equals(EstadoPedidoEnum.RECIBIDO)) {
            Pago pago = new Pago();
            pago.setIdPedido(pedido.getId());
            pago.setIdEmpresa(pedido.getIdEmpresa());
            pago.setFechaPago(new Date());
            pago.setImporte(pedido.getCosteTotal());
            pago.setMetodoPago(pedido.getMetodoPago());
            pagoRepository.save(pago);

            HistorialCompra historialCompra = new HistorialCompra();
            historialCompra.setIdPedido(pedido.getId());
            historialCompra.setIdCliente(pedido.getCliente().getId());
            historialCompra.setFechaCompra(pedido.getFechaPedido());
            historialCompra.setImporte(pedido.getCosteTotal());
            historialCompraRepository.save(historialCompra);
        }

        // Si se ha cancelado el pedido, se actualiza el stock de los productos
        if (nuevoEstado.equals(EstadoPedidoEnum.CANCELADO)) {
            List<DetallePedido> detalles = detallePedidoRepository.findByIdPedido(idPedido);
            for (DetallePedido detalle : detalles) {
                productoService.updateStockProducto(detalle.getProducto().getId(), detalle.getCantidad());
            }
        }

        pedido.setFechaActualizacion(new Date());
        pedido.setEstado(nuevoEstado);
        pedidoRepository.save(pedido);
    }

    private BigDecimal calcularCosteTotal(List<DetallePedido> detalles) {
        BigDecimal costeTotal = BigDecimal.ZERO;
        for (DetallePedido detalle : detalles) {
            BigDecimal costeUnitario = productoService.getCosteProducto(detalle.getProducto().getId());
            detalle.setPrecioUnitario(costeUnitario);
            detalle.setSubtotal(costeUnitario.multiply(BigDecimal.valueOf(detalle.getCantidad())));
            costeTotal = costeTotal.add(detalle.getSubtotal());
        }
        return costeTotal;
    }
}
