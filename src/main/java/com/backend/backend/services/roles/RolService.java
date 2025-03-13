package com.backend.backend.services.roles;

import com.backend.backend.dto.RolForm;
import com.backend.backend.exceptions.BusinessException;
import com.backend.backend.filters.RolFilter;
import com.backend.backend.models.roles.Rol;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RolService {

    Rol getRolById(Long id);

    List<Rol> findRolesByIdEmpresa(Long idEmpresa);

    Page<Rol> findRolesByFilter(RolFilter filter) throws BusinessException;

    @Transactional
    Rol createRol(RolForm rolForm) throws BusinessException;

    @Transactional
    Rol updateRol(Long idRol, RolForm rolForm) throws BusinessException;
}
