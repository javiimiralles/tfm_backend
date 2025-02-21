package com.backend.backend.specifications;

import com.backend.backend.filters.ProductoFilter;
import com.backend.backend.models.inventario.Producto;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductoSpecification {

    public static Specification<Producto> withFilters(ProductoFilter filter) {
        return (Root<Producto> root, CriteriaQuery<?> query, CriteriaBuilder cb)  -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }
            if (filter.getIdCategoria() != null) {
                predicates.add(cb.equal(root.get("idCategoria"), filter.getIdCategoria()));
            }
            if (filter.getIdEmpresa() != null) {
                predicates.add(cb.equal(root.get("idEmpresa"), filter.getIdEmpresa()));
            }
            if (filter.getNombre() != null && !filter.getNombre().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("nombre")), "%" + filter.getNombre().toLowerCase() + "%"));
            }
            if (filter.getDescripcion() != null && !filter.getDescripcion().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("descripcion")), "%" + filter.getDescripcion().toLowerCase() + "%"));
            }
            if (filter.getPrecioVenta() != null) {
                predicates.add(cb.equal(root.get("precioVenta"), filter.getPrecioVenta()));
            }
            if (filter.getCoste() != null) {
                predicates.add(cb.equal(root.get("coste"), filter.getCoste()));
            }
            if (filter.getStock() != null) {
                predicates.add(cb.equal(root.get("stock"), filter.getStock()));
            }
            if (filter.getFechaBaja() != null) {
                predicates.add(cb.equal(root.get("fechaBaja"), filter.getFechaBaja()));
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
