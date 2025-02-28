package com.backend.backend.specifications;

import com.backend.backend.filters.ProveedorFilter;
import com.backend.backend.models.proveedores.Proveedor;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProveedorSpecification {

    public static Specification<Proveedor> withFilters(ProveedorFilter filter) {
        return (Root<Proveedor> root, CriteriaQuery<?> query, CriteriaBuilder cb)  -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getIdEmpresa() != null) {
                predicates.add(cb.equal(root.get("idEmpresa"), filter.getIdEmpresa()));
            }
            if (filter.getNombre() != null && !filter.getNombre().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + filter.getNombre().toLowerCase() + "%"));
            }
            if (filter.getEmail() != null && !filter.getEmail().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + filter.getEmail().toLowerCase() + "%"));
            }

            if (filter.getQuery() != null && !filter.getQuery().trim().isEmpty()) {
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("nombre")), "%" + filter.getQuery().toLowerCase() + "%"),
                    cb.like(cb.lower(root.get("email")), "%" + filter.getQuery().toLowerCase() + "%")
                ));
            }

            predicates.add(cb.isNull(root.get("fechaBaja")));

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
