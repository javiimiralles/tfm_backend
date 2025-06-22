package com.backend.backend.services.empleados;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.backend.backend.dto.EmpleadoDTO;
import com.backend.backend.filters.EmpleadoFilter;
import com.backend.backend.models.empleados.Empleado;
import com.backend.backend.services.BaseTest;

public class EmpleadoServiceTest extends BaseTest {

    @Autowired
    private EmpleadoService empleadoService;


    @BeforeEach
    public void setUp() {
        setUpBase();
    }

    @Test
    public void testGetEmpleadoById() {
        Empleado empleado = empleadoService.getEmpleadoById(idEmpleadoBase);
        assertNotNull(empleado);
        assertEquals(idEmpleadoBase, empleado.getId());
        assertEquals(idEmpresaBase, empleado.getIdEmpresa());
        assertEquals("Javier", empleado.getNombre());
    }

    @Test
    public void testFindEmpleadosDTOByFilter() {
        EmpleadoFilter filter = new EmpleadoFilter();
        filter.setId(idEmpleadoBase);
        filter.setIdEmpresa(idEmpresaBase);
        filter.setNombre("Javier");
        filter.setApellidos("Miralles");
        filter.setActivo(true);
        filter.setQuery("Javier");

        List<EmpleadoDTO> empleados = empleadoService.findEmpleadosDTOByFilter(filter).getContent();
        assertNotNull(empleados);
        assertEquals(1, empleados.size());
        EmpleadoDTO empleadoDTO = empleados.get(0);
        assertEquals(idEmpleadoBase, empleadoDTO.getId());
        assertEquals("Javier", empleadoDTO.getNombre());
    }

    @Test
    public void testGetEmpleadoByIdUsuario() {
        Empleado empleado = empleadoService.getEmpleadoByIdUsuario(idUsuarioBase);
        assertNotNull(empleado);
        assertEquals(idEmpleadoBase, empleado.getId());
        assertEquals(idEmpresaBase, empleado.getIdEmpresa());
        assertEquals("Javier", empleado.getNombre());
    }

    @Test
    public void testGetEmpleadoByIdUsuarioAndIdEmpresa() {
        Empleado empleado = empleadoService.getEmpleadoByIdUsuarioAndIdEmpresa(idUsuarioBase, idEmpresaBase);
        assertNotNull(empleado);
        assertEquals(idEmpleadoBase, empleado.getId());
        assertEquals(idEmpresaBase, empleado.getIdEmpresa());
        assertEquals("Javier", empleado.getNombre());
    }
    
}
