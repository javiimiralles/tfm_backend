package com.backend.backend.specifications;

import com.backend.backend.filters.RolFilter;
import com.backend.backend.models.roles.Rol;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class RolSpecification {

    public static Specification<Rol> withFilters(RolFilter filter) {
        return (Root<Rol> root, CriteriaQuery<?> query, CriteriaBuilder cb)  -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getIdEmpresa() != null) {
                predicates.add(cb.equal(root.get("idEmpresa"), filter.getIdEmpresa()));
            }
            if (filter.getNombre() != null && !filter.getNombre().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + filter.getNombre().toLowerCase() + "%"));
            }

            if (filter.getQuery() != null && !filter.getQuery().trim().isEmpty()) {
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("nombre")), "%" + filter.getQuery().toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("descripcion")), "%" + filter.getQuery().toLowerCase() + "%")
                ));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
