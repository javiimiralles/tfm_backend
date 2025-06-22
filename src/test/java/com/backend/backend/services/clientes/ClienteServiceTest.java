package com.backend.backend.services.clientes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.backend.backend.dto.ClienteDTO;
import com.backend.backend.filters.ClienteFilter;
import com.backend.backend.models.clientes.Cliente;
import com.backend.backend.services.BaseTest;

@SpringBootTest
public class ClienteServiceTest extends BaseTest {

    @Autowired
    private ClienteService clienteService;

    @BeforeEach
    public void setUp() {
        setUpBase();

        Cliente cliente = new Cliente();
        cliente.setNombre("Cliente de Prueba");
        cliente.setEmail("cliente@gmail.com");
        cliente.setTelefono("123456789");
        cliente.setIdEmpresa(idEmpresaBase);

        clienteService.createCliente(cliente, idUsuarioBase);
    }

    @Test
    public void testGeneral() {
        Long idCliente = testFindClientesByEmpresa();
        testGetClienteById(idCliente);
        testFindClientesDTOByFilter();
        testUpdateCliente(idCliente);
        testDeleteCliente(idCliente);
    }

    private Long testFindClientesByEmpresa() {
        List<Cliente> clientes = clienteService.findClientesByEmpresa(idEmpresaBase);
        assertEquals(1, clientes.size());
        return clientes.get(0).getId();
    }

    private void testGetClienteById(Long idCliente) {
        Cliente cliente = clienteService.getClienteById(idCliente);
        assertEquals("Cliente de Prueba", cliente.getNombre());
    }

    private void testFindClientesDTOByFilter() {
        ClienteFilter filter = new ClienteFilter();
        filter.setIdEmpresa(idEmpresaBase);
        filter.setQuery("Cliente");
        filter.setNombre("Cliente de Prueba");
        filter.setEmail("cliente");

        List<ClienteDTO> clientes = clienteService.findClientesDTOByFilter(filter).getContent();
        assertEquals(1, clientes.size());
        assertEquals("Cliente de Prueba", clientes.get(0).getNombre());
    }

    private void testUpdateCliente(Long idCliente) {
        Cliente cliente = clienteService.getClienteById(idCliente);
        cliente.setNombre("Cliente Actualizado");
        clienteService.updateCliente(idCliente, cliente, idUsuarioBase);

        Cliente updatedCliente = clienteService.getClienteById(idCliente);
        assertEquals("Cliente Actualizado", updatedCliente.getNombre());
    }

    private void testDeleteCliente(Long idCliente) {
        clienteService.deleteCliente(idCliente, idUsuarioBase);
        Cliente deletedCliente = clienteService.getClienteById(idCliente);
        assertNotNull(deletedCliente.getFechaBaja());
    }
}
