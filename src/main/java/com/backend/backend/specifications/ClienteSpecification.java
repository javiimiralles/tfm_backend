package com.backend.backend.specifications;

import com.backend.backend.filters.ClienteFilter;
import com.backend.backend.models.Cliente;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ClienteSpecification {

    public static Specification<Cliente> withFilters(ClienteFilter filter) {
        return (Root<Cliente> root, CriteriaQuery<?> query, CriteriaBuilder cb)  -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }

            if (filter.getIdEmpresa() != null) {
                predicates.add(cb.equal(root.get("idEmpresa"), filter.getIdEmpresa()));
            }
            if (filter.getNombre() != null && !filter.getNombre().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + filter.getNombre().toLowerCase() + "%"));
            }
            if (filter.getApellidos() != null && !filter.getApellidos().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("apellidos")), "%" + filter.getApellidos().toLowerCase() + "%"));
            }
            if (filter.getEmail() != null && !filter.getEmail().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + filter.getEmail().toLowerCase() + "%"));
            }
            if (filter.getFechaAlta() != null) {
                predicates.add(cb.equal(root.get("fechaAlta"), filter.getFechaAlta()));
            }

            if (filter.getQuery() != null && !filter.getQuery().isEmpty()) {
                String queryText = "%" + filter.getQuery().toLowerCase() + "%";
                Predicate searchPredicate = cb.or(
                        cb.like(cb.lower(root.get("nombre")), queryText),
                        cb.like(cb.lower(root.get("apellidos")), queryText),
                        cb.like(cb.lower(root.get("email")), queryText)
                );
                predicates.add(searchPredicate);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
