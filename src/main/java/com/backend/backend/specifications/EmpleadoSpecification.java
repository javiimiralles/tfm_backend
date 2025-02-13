package com.backend.backend.specifications;

import com.backend.backend.filters.EmpleadoFilter;
import com.backend.backend.models.Empleado;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class EmpleadoSpecification {

    public static Specification<Empleado> withFilters(EmpleadoFilter filter) {
        return (Root<Empleado> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }
            if (filter.getIdEmpresa() != null) {
                predicates.add(cb.equal(root.get("idEmpresa"), filter.getIdEmpresa()));
            }
            if (filter.getIdUsuario() != null) {
                predicates.add(cb.equal(root.get("idUsuario"), filter.getIdUsuario()));
            }
            if (filter.getNombre() != null && !filter.getNombre().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + filter.getNombre().toLowerCase() + "%"));
            }
            if (filter.getApellidos() != null && !filter.getApellidos().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("apellidos")), "%" + filter.getApellidos().toLowerCase() + "%"));
            }
            if (filter.getActivo() != null) {
                if (Boolean.TRUE.equals(filter.getActivo())) {
                    predicates.add(cb.isNull(root.get("fechaBaja")));
                } else {
                    predicates.add(cb.isNotNull(root.get("fechaBaja")));
                }
            }
            if (filter.getRol() != null && !filter.getRol().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("rol")), filter.getRol().toLowerCase()));
            }

            if (filter.getQuery() != null && !filter.getQuery().isEmpty()) {
                String queryText = "%" + filter.getQuery().toLowerCase() + "%";
                Predicate searchPredicate = cb.or(
                        cb.like(cb.lower(root.get("nombre")), queryText),
                        cb.like(cb.lower(root.get("apellidos")), queryText)
                );
                predicates.add(searchPredicate);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
