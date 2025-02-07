package com.backend.backend.services;

import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.models.Cliente;
import com.backend.backend.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioService usuarioService;
    private final EmpleadoService empleadoService;

    Logger logger = Logger.getLogger(ClienteServiceImpl.class.getName());

    public ClienteServiceImpl(ClienteRepository clienteRepository, UsuarioService usuarioService, EmpleadoService empleadoService) {
        this.clienteRepository = clienteRepository;
        this.usuarioService = usuarioService;
        this.empleadoService = empleadoService;
    }

    @Override
    public List<Cliente> findClientesByEmpresa(Long idEmpresa) {
        logger.log(Level.INFO, "Obteniendo clientes de la empresa: {}", idEmpresa);
        return clienteRepository.findByIdEmpresa(idEmpresa);
    }

    @Transactional
    @Override
    public void createCliente(Cliente cliente, Long idResponsable) throws BusinessException {
        logger.log(Level.INFO, "Creando cliente: {}", cliente);

        if (!userExists(idResponsable)) {
            logger.log(Level.WARNING, "El usuario con id {} no existe", idResponsable);
            throw new BusinessException("El usuario no existe");
        }

        if (!validateEmployee(cliente.getIdEmpresa(), idResponsable)) {
            logger.log(Level.WARNING, "El empleado con idUsuario {} no pertenece a la empresa {}", new Object[]{idResponsable, cliente.getIdEmpresa()});
            throw new BusinessException("El empleado responsable de creación no pertenece a la empresa");
        }

        cliente.setFechaAlta(new Date());
        cliente.setIdRespAlta(idResponsable);
        clienteRepository.save(cliente);
    }

    private boolean userExists(Long id) {
        return usuarioService.getUsuarioDTOById(id) != null;
    }

    private boolean validateEmployee(Long idEmpresa, Long idUsuario) {
        return empleadoService.getEmpleadoByIdUsuarioAndIdEmpresa(idUsuario, idEmpresa) != null;
    }
}
