package com.backend.backend.services.roles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.backend.backend.dto.RolForm;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.RolFilter;
import com.backend.backend.models.roles.Accion;
import com.backend.backend.models.roles.Rol;
import com.backend.backend.services.BaseTest;

public class RolServiceTest extends BaseTest {
    
    @Autowired
    private RolService rolService;

    @Autowired
    private AccionService accionService;

    private Rol rolBase;

    private List<Accion> accionesBase;

    @BeforeEach
    public void setUp() {
        setUpBase();

        Accion accion1 = new Accion();
        accion1.setNombre("Accion 1");
        accion1.setDescripcion("Descripcion de la accion 1");
        accion1.setAccion("ACCION_1");
        accion1.setGrupo("Grupo 1");
        accion1 = accionService.createAccion(accion1);

        Accion accion2 = new Accion();
        accion2.setNombre("Accion 2");
        accion2.setDescripcion("Descripcion de la accion 2");
        accion2.setAccion("ACCION_2");
        accion2.setGrupo("Grupo 2");
        accion2 = accionService.createAccion(accion2);

        accionesBase = List.of(accion1, accion2);

        RolForm rolForm = new RolForm();
        rolForm.setIdEmpresa(idEmpresaBase);
        rolForm.setNombre("Rol de prueba");
        rolForm.setDescripcion("Descripcion del rol de prueba");
        rolForm.setAcciones(accionesBase);

        rolBase = rolService.createRol(rolForm);
        assertNotNull(rolBase);
        assertNotNull(rolBase.getId());
    }

    @Test
    public void testGetRolById() {
        Rol rol = rolService.getRolById(rolBase.getId());
        assertNotNull(rol);
        assertEquals(rolBase.getId(), rol.getId());
        assertEquals(rolBase.getNombre(), rol.getNombre());
    }

    @Test
    public void testFindRolesByIdEmpresa() {
        List<Rol> roles = rolService.findRolesByIdEmpresa(idEmpresaBase);
        assertNotNull(roles);
        assertEquals(2, roles.size());
        assertEquals("ADMIN", roles.get(0).getNombre());
        assertEquals("Rol de prueba", roles.get(1).getNombre());
    }

    @Test
    public void testFindRolesByFilter() {
        RolFilter filter = new RolFilter();

        assertThrows(BusinessException.class, 
            () -> rolService.findRolesByFilter(filter), "El idEmpresa es obligatorio");

        filter.setIdEmpresa(idEmpresaBase);
        filter.setNombre("Rol de prueba");
        List<Rol> roles = rolService.findRolesByFilter(filter).getContent();
        assertNotNull(roles);
        assertEquals(1, roles.size());
        assertEquals(rolBase.getId(), roles.get(0).getId());
    }

    @Test
    public void testUpdateRol() {
        RolForm rolForm = new RolForm();
        rolForm.setIdEmpresa(idEmpresaBase);
        rolForm.setNombre("Rol de prueba actualizado");
        rolForm.setDescripcion("Descripcion del rol de prueba actualizado");
        rolForm.setAcciones(accionesBase);

        Rol rolActualizado = rolService.updateRol(rolBase.getId(), rolForm);
        assertNotNull(rolActualizado);
        assertEquals(rolForm.getNombre(), rolActualizado.getNombre());
        assertEquals(rolForm.getDescripcion(), rolActualizado.getDescripcion());
    }

}
