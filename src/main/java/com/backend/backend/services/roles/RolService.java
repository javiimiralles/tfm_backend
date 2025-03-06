package com.backend.backend.services.roles;

import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.RolFilter;
import com.backend.backend.models.roles.Rol;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RolService {

    List<Rol> findRolesByIdEmpresa(Long idEmpresa);

    Page<Rol> findRolesByFilter(RolFilter filter) throws BusinessException;

    @Transactional
    void createRol(Rol rol);
}
