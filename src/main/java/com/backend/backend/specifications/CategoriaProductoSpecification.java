package com.backend.backend.specifications;

import com.backend.backend.filters.CategoriaProductoFilter;
import com.backend.backend.models.inventario.CategoriaProducto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CategoriaProductoSpecification {

    public static Specification<CategoriaProducto> withFilters(CategoriaProductoFilter filter) {
        return (Root<CategoriaProducto> root,CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getIdEmpresa() != null) {
                predicates.add(cb.equal(root.get("idEmpresa"), filter.getIdEmpresa()));
            }

            if (filter.getQuery() != null && !filter.getQuery().isEmpty()) {
                String queryText = "%" + filter.getQuery().toLowerCase() + "%";
                Predicate searchPredicate = cb.or(
                        cb.like(cb.lower(root.get("nombre")), queryText),
                        cb.like(cb.lower(root.get("descripcion")), queryText)
                );
                predicates.add(searchPredicate);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
