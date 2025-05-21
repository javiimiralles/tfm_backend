package com.backend.backend.services.dashboard;

import com.backend.backend.dto.DashboardSummaryDTO;
import com.backend.backend.enums.EstadoPedidoEnum;
import com.backend.backend.enums.EstadoPedidoProveedorEnum;
import com.backend.backend.models.proveedores.PedidoProveedor;
import com.backend.backend.models.ventas.Pago;
import com.backend.backend.repository.ClienteRepository;
import com.backend.backend.repository.PagoRepository;
import com.backend.backend.repository.PedidoProveedorRepository;
import com.backend.backend.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final PagoRepository pagoRepository;

    private final PedidoProveedorRepository pedidoProveedorRepository;

    private final PedidoRepository pedidoRepository;

    private final ClienteRepository clienteRepository;

    public DashboardServiceImpl(PagoRepository pagoRepository, PedidoProveedorRepository pedidoProveedorRepository,
                                PedidoRepository pedidoRepository, ClienteRepository clienteRepository) {
        this.pagoRepository = pagoRepository;
        this.pedidoProveedorRepository = pedidoProveedorRepository;
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public DashboardSummaryDTO getDashboardSummary(Long idEmpresa) {
        DashboardSummaryDTO summary = new DashboardSummaryDTO();

        List<Pago> ultimosPagos = pagoRepository.findPagosLast30Days(new Date(), idEmpresa);
        if (ultimosPagos != null && !ultimosPagos.isEmpty()) {
            summary.setTotalFacturado(ultimosPagos.stream()
                    .map(Pago::getImporte)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
        } else {
            summary.setTotalFacturado(BigDecimal.ZERO);
        }

        List<PedidoProveedor> ultimosPedidos = pedidoProveedorRepository.findPedidosUltimos30DiasConEstado(new Date(), EstadoPedidoProveedorEnum.RECIBIDO, idEmpresa);
        if (ultimosPedidos != null && !ultimosPedidos.isEmpty()) {
            summary.setGastosPedidosProveedores(ultimosPedidos.stream()
                    .map(PedidoProveedor::getCosteTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add));
        } else {
            summary.setGastosPedidosProveedores(BigDecimal.ZERO);
        }

        List<EstadoPedidoEnum> estadosPedidos = List.of(EstadoPedidoEnum.PENDIENTE, EstadoPedidoEnum.PROCESADO, EstadoPedidoEnum.ENVIADO);
        int ventasEnCurso = pedidoRepository.countPedidosUltimos30DiasConEstados(new Date(), idEmpresa, estadosPedidos);
        summary.setVentasEnCurso(ventasEnCurso);

        int clientesNuevos = clienteRepository.countClientesNuevosUltimos30Dias(new Date(), idEmpresa);
        summary.setClientesNuevos(clientesNuevos);

        List<EstadoPedidoProveedorEnum> estadosPedidosProveedor = List.of(EstadoPedidoProveedorEnum.PENDIENTE, EstadoPedidoProveedorEnum.ENVIADO, EstadoPedidoProveedorEnum.RECIBIDO);
        int pedidosProveedores = pedidoProveedorRepository.countPedidosUltimos30DiasConEstados(new Date(), idEmpresa, estadosPedidosProveedor);
        summary.setPedidosProveedores(pedidosProveedores);

        return summary;
    }
}
