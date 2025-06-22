package com.backend.backend.services.empleados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.backend.backend.models.empleados.Empresa;
import com.backend.backend.services.BaseTest;

public class EmpresaServiceTest extends BaseTest {

    @Autowired
    private EmpresaService empresaService;

    @BeforeEach
    public void setUp() {
        setUpBase();
    }

    @Test
    public void testGetEmpresaById() {
        Empresa empresa = empresaService.getEmpresaById(idEmpresaBase);
        assertNotNull(empresa);
        assertEquals(idEmpresaBase, empresa.getId());
        assertEquals("Empresa de Prueba", empresa.getNombre());
    }

    @Test
    public void testCreateEmpresa() {
        Empresa empresa = new Empresa();
        empresa.setNombre("Nueva Empresa");
        empresa.setRazonSocial("Nueva Empresa S.L.");
        empresa.setEmail("nueva@empresa.com");
        empresaService.createEmpresa(empresa);
        assertNotNull(empresa.getId());
    }
    
}
