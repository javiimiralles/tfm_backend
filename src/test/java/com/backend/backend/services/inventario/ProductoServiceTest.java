package com.backend.backend.services.inventario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.backend.backend.dto.ProductoDTO;
import com.backend.backend.filters.ProductoFilter;
import com.backend.backend.models.inventario.Producto;
import com.backend.backend.services.BaseTest;

public class ProductoServiceTest extends BaseTest {

    @Autowired
    private ProductoService productoService;

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
    }

    @Test
    public void testGeneral() throws IOException {
        Long idProducto = testFindProductosByEmpresa();
        testGetProductoById(idProducto);
        testFindProductosDTOByFilter(idProducto);
        testUpdateProducto(idProducto);
        testUpdateStockProducto(idProducto);
    }

    private Long testFindProductosByEmpresa() {
        List<Producto> productos = productoService.findProductosByEmpresa(idEmpresaBase);
        assertNotNull(productos);
        assertFalse(productos.isEmpty());
        assertEquals(1, productos.size());
        assertEquals("Producto 1", productos.get(0).getNombre());
        return productos.get(0).getId();
    }

    private void testGetProductoById(Long idProducto) {
        Producto producto = productoService.getProductoById(idProducto);
        assertNotNull(producto);
        assertEquals("Producto 1", producto.getNombre());
    }

    private void testFindProductosDTOByFilter(Long idProducto) {
        ProductoFilter filter = new ProductoFilter();
        filter.setIdEmpresa(idEmpresaBase);
        filter.setQuery("Producto 1");
        filter.setId(idProducto);
        filter.setDescripcion("Descripción del producto 1");
        filter.setStock(50);
        List<ProductoDTO> productos = productoService.findProductosDTOByFilter(filter).getContent();
        assertNotNull(productos);
        assertFalse(productos.isEmpty());
        assertEquals(1, productos.size());
        assertEquals("Producto 1", productos.get(0).getNombre());
    }

    private void testUpdateProducto(Long idProducto) throws IOException {
        Producto producto = productoService.getProductoById(idProducto);
        assertNotNull(producto);
        producto.setNombre("Producto Actualizado");
        productoService.updateProducto(idProducto, producto, null, idUsuarioBase, false);

        Producto updatedProducto = productoService.getProductoById(idProducto);
        assertNotNull(updatedProducto);
        assertEquals("Producto Actualizado", updatedProducto.getNombre());
    }

    private void testUpdateStockProducto(Long idProducto) {
        Producto producto = productoService.getProductoById(idProducto);
        assertNotNull(producto);
        productoService.updateStockProducto(idProducto, 100);

        Producto updatedProducto = productoService.getProductoById(idProducto);
        assertNotNull(updatedProducto);
        assertEquals(150, updatedProducto.getStock());

        productoService.updateStockProducto(idProducto, -50);

        updatedProducto = productoService.getProductoById(idProducto);
        assertNotNull(updatedProducto);
        assertEquals(100, updatedProducto.getStock());
    }


    
}
