package com.backend.backend.services;

import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.models.Cliente;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClienteService {

    List<Cliente> findClientesByEmpresa(Long idEmpresa);

    @Transactional
    void createCliente(Cliente cliente, Long idResponsable) throws BusinessException;
}
