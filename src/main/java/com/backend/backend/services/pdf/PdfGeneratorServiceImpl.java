package com.backend.backend.services.pdf;

import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.models.clientes.Cliente;
import com.backend.backend.models.empleados.Empresa;
import com.backend.backend.models.ventas.DetallePedido;
import com.backend.backend.models.ventas.Factura;
import com.backend.backend.services.empleados.EmpresaService;
import com.backend.backend.services.ventas.DetallePedidoService;
import com.backend.backend.services.ventas.FacturaService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

    private final TemplateEngine templateEngine;

    private final FacturaService facturaService;

    private final EmpresaService empresaService;

    private final DetallePedidoService detallePedidoService;

    public PdfGeneratorServiceImpl(TemplateEngine templateEngine, @Lazy FacturaService facturaService,
                                   @Lazy EmpresaService empresaService, DetallePedidoService detallePedidoService) {
        this.templateEngine = templateEngine;
        this.facturaService = facturaService;
        this.empresaService = empresaService;
        this.detallePedidoService = detallePedidoService;
    }

    @Override
    public byte[] generateFacturaPdf(Long idFactura) {
        Context context = new Context();
        Factura factura = facturaService.getFacturaById(idFactura);
        if (factura == null) {
            throw new BusinessException("Factura no encontrada");
        }

        Empresa empresa = empresaService.getEmpresaById(factura.getIdEmpresa());
        if (empresa == null) {
            throw new BusinessException("Empresa no encontrada");
        }

        List<DetallePedido> detalles = detallePedidoService.findDetallesPedidoByIdPedido(factura.getPedido().getId());
        if (detalles == null || detalles.isEmpty()) {
            throw new BusinessException("No se encontraron detalles de pedido para la factura");
        }

        // Datos de factura
        context.setVariable("fechaFactura", factura.getFechaFactura());
        context.setVariable("numeroFactura", factura.getNumeroFactura());
        context.setVariable("fechaVencimiento", factura.getFechaVencimiento());
        context.setVariable("totalFactura", factura.getImporte());
        context.setVariable("observaciones", factura.getPedido().getObservaciones());

        // Datos de la empresa
        context.setVariable("nombreEmpresa", empresa.getNombre());
        context.setVariable("razonSocialEmpresa", empresa.getRazonSocial());
        context.setVariable("cifEmpresa", empresa.getCif());
        context.setVariable("direccionEmpresa", empresa.getDireccion());
        context.setVariable("telefonoEmpresa", empresa.getTelefono());
        context.setVariable("emailEmpresa", empresa.getEmail());

        // Datos del cliente
        Cliente cliente = factura.getCliente();
        String nombreCliente = cliente.getNombre() + (cliente.getApellidos() != null ? " " + cliente.getApellidos() : "");
        context.setVariable("nombreCliente", nombreCliente);
        context.setVariable("nifCliente", cliente.getNif());
        context.setVariable("direccionCliente", cliente.getDireccion());
        context.setVariable("telefonoCliente", cliente.getTelefono());
        context.setVariable("emailCliente", cliente.getEmail());

        // Datos de los detalles del pedido
        context.setVariable("detalles", detalles);

        String html = templateEngine.process("factura", context);

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(baos);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new BusinessException("Error al generar PDF", e);
        }
    }
}
