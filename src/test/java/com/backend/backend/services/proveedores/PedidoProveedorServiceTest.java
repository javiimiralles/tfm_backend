package com.backend.backend.services.proveedores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.backend.backend.dto.DatosPedidoProveedor;
import com.backend.backend.dto.ProveedorDTO;
import com.backend.backend.enums.EstadoPedidoProveedorEnum;
import com.backend.backend.filters.PedidoProveedorFilter;
import com.backend.backend.filters.ProveedorFilter;
import com.backend.backend.models.inventario.Producto;
import com.backend.backend.models.proveedores.DetallePedidoProveedor;
import com.backend.backend.models.proveedores.PedidoProveedor;
import com.backend.backend.models.proveedores.Proveedor;
import com.backend.backend.services.BaseTest;
import com.backend.backend.services.inventario.ProductoService;

public class PedidoProveedorServiceTest extends BaseTest  {

    @Autowired
    private PedidoProveedorService pedidoProveedorService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProveedorService proveedorService;

    private Long idPedidoProveedorBase;

    @BeforeEach
    public void setUp() throws IOException {
        setUpBase();

        Producto producto = new Producto();
        producto.setNombre("Producto 1");
        producto.setDescripcion("Descripción del producto 1");
        producto.setIdEmpresa(idEmpresaBase);
        producto.setPrecioVenta(BigDecimal.valueOf(100.00));
        producto.setImpuestoVenta(BigDecimal.valueOf(0.21));
        producto.setCoste(BigDecimal.valueOf(80.00));
        producto.setImpuestoCompra(BigDecimal.valueOf(0.21));
        producto.setStock(50);
        productoService.createProducto(producto, null, idUsuarioBase);

        Producto createdProducto = productoService.findProductosByEmpresa(idEmpresaBase).get(0);

        DetallePedidoProveedor detallePedido = new DetallePedidoProveedor();
        detallePedido.setProducto(createdProducto);
        detallePedido.setCantidad(10);

        Proveedor proveedor = new Proveedor();
        proveedor.setNombre("Proveedor 1");
        proveedor.setIdEmpresa(idEmpresaBase);
        proveedor.setEmail("proveedor1@example.com");
        proveedorService.createProveedor(proveedor, idUsuarioBase);
        ProveedorFilter proveedorFilter = new ProveedorFilter();
        proveedorFilter.setIdEmpresa(idEmpresaBase);
        ProveedorDTO createdProveedor = proveedorService.findProveedoresDTOByFilter(proveedorFilter).getContent().get(0);

        List<DetallePedidoProveedor> detallesPedido = List.of(detallePedido);

        DatosPedidoProveedor datosPedido = new DatosPedidoProveedor();
        datosPedido.setIdEmpresa(idEmpresaBase);
        datosPedido.setIdProveedor(createdProveedor.getId());
        datosPedido.setDetallesPedido(detallesPedido);

        pedidoProveedorService.realizarPedidoProveedor(datosPedido, idUsuarioBase);

        PedidoProveedorFilter pedidoProveedorFilter = new PedidoProveedorFilter();
        pedidoProveedorFilter.setIdEmpresa(idEmpresaBase);

        PedidoProveedor pedidoProveedor = pedidoProveedorService.findPedidosProveedorByFilter(pedidoProveedorFilter).getContent().get(0);
        assertNotNull(pedidoProveedor);
        assertEquals(EstadoPedidoProveedorEnum.PENDIENTE, pedidoProveedor.getEstado());
        assertEquals(idEmpresaBase, pedidoProveedor.getIdEmpresa());
        assertEquals(pedidoProveedor.getCosteTotal(), BigDecimal.valueOf(800.00));
        idPedidoProveedorBase = pedidoProveedor.getId();
    }

    @Test
    public void testGetPedidoProveedorById() {
        PedidoProveedor pedido = pedidoProveedorService.getPedidoProveedorById(idPedidoProveedorBase);
        assertNotNull(pedido);
        assertEquals(idPedidoProveedorBase, pedido.getId());
    }

    @Test
    public void testFindPedidosProveedorByFilter() {
        PedidoProveedorFilter filter = new PedidoProveedorFilter();
        filter.setId(idPedidoProveedorBase);
        filter.setIdEmpresa(idEmpresaBase);
        List<PedidoProveedor> pedidos = pedidoProveedorService.findPedidosProveedorByFilter(filter).getContent();
        assertNotNull(pedidos);
        assertEquals(1, pedidos.size());
        assertEquals(idPedidoProveedorBase, pedidos.get(0).getId());
    }

    @Test
    public void testUpdateEstadoPedidoProveedor() {
        pedidoProveedorService.updateEstadoPedidoProveedor(idPedidoProveedorBase, EstadoPedidoProveedorEnum.ENVIADO);
        PedidoProveedor pedidoActualizado = pedidoProveedorService.getPedidoProveedorById(idPedidoProveedorBase);
        assertNotNull(pedidoActualizado);
        assertEquals(EstadoPedidoProveedorEnum.ENVIADO, pedidoActualizado.getEstado());

        pedidoProveedorService.updateEstadoPedidoProveedor(idPedidoProveedorBase, EstadoPedidoProveedorEnum.RECIBIDO);
        pedidoActualizado = pedidoProveedorService.getPedidoProveedorById(idPedidoProveedorBase);
        assertNotNull(pedidoActualizado);
        assertEquals(EstadoPedidoProveedorEnum.RECIBIDO, pedidoActualizado.getEstado());
    }
    
}
