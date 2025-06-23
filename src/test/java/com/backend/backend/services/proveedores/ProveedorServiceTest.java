package com.backend.backend.services.proveedores;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.backend.backend.dto.ProveedorDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.ProveedorFilter;
import com.backend.backend.models.proveedores.Proveedor;
import com.backend.backend.services.BaseTest;

public class ProveedorServiceTest extends BaseTest {

    @Autowired
    private ProveedorService proveedorService;

    private Long idProveedorBase;

    @BeforeEach
    public void setUp() {
        setUpBase();

        Proveedor proveedor = new Proveedor();
        proveedor.setNombre("Proveedor Base");
        proveedor.setIdEmpresa(idEmpresaBase);
        proveedor.setEmail("proveedor@example.com");
        proveedorService.createProveedor(proveedor, idUsuarioBase);
        idProveedorBase = proveedor.getId();
    }

    @Test
    public void testGetProveedorById() {
        Proveedor proveedor = proveedorService.getProveedorById(idProveedorBase);
        assertNotNull(proveedor);
        assertEquals(idProveedorBase, proveedor.getId());
        assertEquals("Proveedor Base", proveedor.getNombre());
    }

    @Test
    public void testFindProveedoresByFilter() {
        ProveedorFilter filter = new ProveedorFilter();

        assertThrows(BusinessException.class, () -> {
            proveedorService.findProveedoresDTOByFilter(filter);
        }, "El idEmpresa es obligatorio");

        filter.setIdEmpresa(idEmpresaBase);
        filter.setNombre("Proveedor Base");
        filter.setEmail("proveedor@example.com");

        List<ProveedorDTO> proveedores = proveedorService.findProveedoresDTOByFilter(filter).getContent();
        assertNotNull(proveedores);
        assertEquals(1, proveedores.size());
        ProveedorDTO proveedorDTO = proveedores.get(0);
        assertEquals(idProveedorBase, proveedorDTO.getId());
        assertEquals("Proveedor Base", proveedorDTO.getNombre());
    }

    @Test
    public void testUpdateProveedor() {
        Proveedor proveedor = proveedorService.getProveedorById(idProveedorBase);
        assertNotNull(proveedor);
        proveedor.setNombre("Proveedor Actualizado");

        assertThrows(BusinessException.class, () -> {
            proveedorService.updateProveedor(999L, proveedor, idUsuarioBase);
        }, "El proveedor no existe");

        assertThrows(BusinessException.class, () -> {
            proveedorService.updateProveedor(idProveedorBase, proveedor, 999L);
        }, "El usuario responsable no existe o no pertenece a la empresa");

        proveedorService.updateProveedor(idProveedorBase, proveedor, idUsuarioBase);
        Proveedor updatedProveedor = proveedorService.getProveedorById(idProveedorBase);
        assertNotNull(updatedProveedor);
        assertEquals("Proveedor Actualizado", updatedProveedor.getNombre());
    }

    @Test
    public void testDeleteProveedor() {
        assertThrows(BusinessException.class, () -> {
            proveedorService.deleteProveedor(999L, idUsuarioBase);
        }, "El proveedor no existe");

        assertThrows(BusinessException.class, () -> {
            proveedorService.deleteProveedor(idProveedorBase, 999L);
        }, "El usuario responsable no existe o no pertenece a la empresa");

        proveedorService.deleteProveedor(idProveedorBase, idUsuarioBase);
        Proveedor deletedProveedor = proveedorService.getProveedorById(idProveedorBase);
        assertNotNull(deletedProveedor.getFechaBaja());
    }
    
}
