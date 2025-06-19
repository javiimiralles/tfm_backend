package com.backend.backend.services.dashboard;

import com.backend.backend.dto.DashboardIcomesExpensesDTO;
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
import java.math.RoundingMode;
import java.util.ArrayList;
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
        Date fechaLimite = new Date(System.currentTimeMillis() - 30L * 24 * 60 * 60 * 1000); // 30 días atrás

        List<EstadoPedidoEnum> estadosPedidos = List.of(EstadoPedidoEnum.PENDIENTE, EstadoPedidoEnum.PROCESADO, EstadoPedidoEnum.ENVIADO);
        int ventasEnCurso = pedidoRepository.countPedidosConFechaLimiteYEstados(fechaLimite, idEmpresa, estadosPedidos);
        summary.setVentasEnCurso(ventasEnCurso);

        int clientesNuevos = clienteRepository.countClientesNuevosConFechaLimite(fechaLimite, idEmpresa);
        summary.setClientesNuevos(clientesNuevos);

        List<EstadoPedidoProveedorEnum> estadosPedidosProveedor = List.of(EstadoPedidoProveedorEnum.PENDIENTE, EstadoPedidoProveedorEnum.ENVIADO, EstadoPedidoProveedorEnum.RECIBIDO);
        int pedidosProveedores = pedidoProveedorRepository.countPedidosConFechaLimiteYEstados(fechaLimite, idEmpresa, estadosPedidosProveedor);
        summary.setPedidosProveedores(pedidosProveedores);

        return summary;
    }

    @Override
    public DashboardIcomesExpensesDTO getDashboardIcomesExpenses(Long idEmpresa) {
        DashboardIcomesExpensesDTO dto = new DashboardIcomesExpensesDTO();
        Date fechaLimite = new Date(System.currentTimeMillis() - 6L * 30 * 24 * 60 * 60 * 1000); // 6 meses atrás

        List<Pago> pagos = pagoRepository.findPagosConFechaLimite(fechaLimite, idEmpresa);
        BigDecimal totalIngresos = pagos.stream()
                .map(Pago::getImporte)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotalIngresos(totalIngresos);

        List<PedidoProveedor> pedidosProveedores = pedidoProveedorRepository.findPedidosConFechaLimiteYEstado(fechaLimite, EstadoPedidoProveedorEnum.RECIBIDO, idEmpresa);
        BigDecimal totalGastos = pedidosProveedores.stream()
                .map(PedidoProveedor::getCosteTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotalGastos(totalGastos);

        if (totalGastos.compareTo(BigDecimal.ZERO) > 0) {
            dto.setRatioBeneficio(totalIngresos.subtract(totalGastos).divide(totalGastos, RoundingMode.HALF_DOWN).doubleValue());
        } else {
            dto.setRatioBeneficio(0.0);
        }

        List<BigDecimal> ingresosUltimos6Meses = new ArrayList<>();
        List<BigDecimal> gastosUltimos6Meses = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            Date fechaInicio = new Date(System.currentTimeMillis() - (i + 1) * 30L * 24 * 60 * 60 * 1000);
            Date fechaFin = new Date(System.currentTimeMillis() - i * 30L * 24 * 60 * 60 * 1000);

            BigDecimal ingresosMes = pagos.stream()
                    .filter(p -> p.getFechaPago().after(fechaInicio) && p.getFechaPago().before(fechaFin))
                    .map(Pago::getImporte)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            ingresosUltimos6Meses.add(ingresosMes);

            BigDecimal gastosMes = pedidosProveedores.stream()
                    .filter(p -> p.getFechaPedido().after(fechaInicio) && p.getFechaPedido().before(fechaFin))
                    .map(PedidoProveedor::getCosteTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            gastosUltimos6Meses.add(gastosMes);
        }

        dto.setIngresosUltimos6Meses(ingresosUltimos6Meses);
        dto.setGastosUltimos6Meses(gastosUltimos6Meses);

        return dto;
    }


}
