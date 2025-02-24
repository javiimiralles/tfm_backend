package com.backend.backend.services.clientes;

import com.backend.backend.dto.ClienteDTO;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.ClienteFilter;
import com.backend.backend.models.clientes.Cliente;
import com.backend.backend.repository.ClienteRepository;
import com.backend.backend.services.empleados.EmpleadoService;
import com.backend.backend.services.usuarios.UsuarioService;
import com.backend.backend.specifications.ClienteSpecification;
import com.backend.backend.utils.MapperUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    private final MapperUtil mapperUtil;

    Logger logger = Logger.getLogger(ClienteServiceImpl.class.getName());

    public ClienteServiceImpl(ClienteRepository clienteRepository, UsuarioService usuarioService,
                              EmpleadoService empleadoService, MapperUtil mapperUtil) {
        this.clienteRepository = clienteRepository;
        this.usuarioService = usuarioService;
        this.empleadoService = empleadoService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public Cliente getClienteById(Long id) {
        logger.log(Level.INFO, "Obteniendo cliente con id: {}", id);
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cliente> findClientesByEmpresa(Long idEmpresa) {
        logger.log(Level.INFO, "Obteniendo clientes de la empresa: {}", idEmpresa);
        return clienteRepository.findByIdEmpresa(idEmpresa);
    }

    @Override
    public Page<ClienteDTO> findClientesDTOByFilter(ClienteFilter filter) throws BusinessException {
        logger.log(Level.INFO, "Obteniendo clientes con filtro: {}", filter);
        if (filter.getIdEmpresa() == null) {
            logger.log(Level.WARNING, "El idEmpresa es obligatorio");
            throw new BusinessException("El idEmpresa es obligatorio");
        }
        Specification<Cliente> spec = ClienteSpecification.withFilters(filter);
        Pageable pageable = filter.getPageable();

        Page<Cliente> clientes = clienteRepository.findAll(spec, pageable);
        return clientes.map(mapperUtil::mapClienteToClienteDTO);
    }

    @Transactional
    @Override
    public void createCliente(Cliente cliente, Long idResponsable) throws BusinessException {
        logger.log(Level.INFO, "Creando cliente: {}", cliente);

        if (!usuarioService.validateUsuarioResponsable(idResponsable, cliente.getIdEmpresa())) {
            throw new BusinessException("El empleado responsable de creación no existe o no pertenece a la empresa");
        }

        cliente.setFechaAlta(new Date());
        cliente.setIdRespAlta(idResponsable);
        clienteRepository.save(cliente);
    }

    @Transactional
    @Override
    public void updateCliente(Long id, Cliente cliente, Long idResponsable) throws BusinessException {
        logger.log(Level.INFO, "Actualizando cliente con id: {}", id);

        if (!usuarioService.validateUsuarioResponsable(idResponsable, cliente.getIdEmpresa())) {
            throw new BusinessException("El empleado responsable de creación no existe o no pertenece a la empresa");
        }

        Cliente clienteToUpdate = clienteRepository.findById(id).orElse(null);
        if (clienteToUpdate == null) {
            logger.log(Level.WARNING, "El cliente con id {} no existe", id);
            throw new BusinessException("El cliente no existe");
        }

        cliente.setFechaModif(new Date());
        cliente.setIdRespModif(idResponsable);
        clienteRepository.save(cliente);
    }

    @Transactional
    @Override
    public void deleteCliente(Long id, Long idResponsable) throws BusinessException {
        logger.log(Level.INFO, "Eliminando cliente con id: {}", id);

        Cliente clienteToDelete = clienteRepository.findById(id).orElse(null);
        if (clienteToDelete == null) {
            logger.log(Level.WARNING, "El cliente con id {} no existe", id);
            throw new BusinessException("El cliente no existe");
        }

        if (!usuarioService.validateUsuarioResponsable(idResponsable, clienteToDelete.getIdEmpresa())) {
            throw new BusinessException("El empleado responsable de creación no existe o no pertenece a la empresa");
        }

        clienteToDelete.setFechaBaja(new Date());
        clienteToDelete.setIdRespBaja(idResponsable);
        clienteRepository.save(clienteToDelete);
    }
}
