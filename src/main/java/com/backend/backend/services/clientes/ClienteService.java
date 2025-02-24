package com.backend.backend.services.clientes;

import com.backend.backend.dto.ClienteDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.ClienteFilter;
import com.backend.backend.models.clientes.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClienteService {

    Cliente getClienteById(Long id);

    List<Cliente> findClientesByEmpresa(Long idEmpresa);

    Page<ClienteDTO> findClientesDTOByFilter(ClienteFilter filter) throws BusinessException;

    @Transactional
    void createCliente(Cliente cliente, Long idResponsable) throws BusinessException;

    @Transactional
    void updateCliente(Long id, Cliente cliente, Long idResponsable) throws BusinessException;

    @Transactional
    void deleteCliente(Long id, Long idResponsable) throws BusinessException;
}
