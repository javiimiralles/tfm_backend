package com.backend.backend.specifications;

import com.backend.backend.filters.PedidoProveedorFilter;
import com.backend.backend.models.proveedores.PedidoProveedor;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PedidoProveedorSpecification {

    public static Specification<PedidoProveedor> withFilters(PedidoProveedorFilter filter) {
        return (Root<PedidoProveedor> root, CriteriaQuery<?> query, CriteriaBuilder cb)  -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getId() != null) {
                predicates.add(cb.equal(root.get("id"), filter.getId()));
            }
            if (filter.getIdProveedor() != null) {
                predicates.add(cb.equal(root.get("proveedor").get("id"), filter.getIdProveedor()));
            }
            if (filter.getIdEmpresa() != null) {
                predicates.add(cb.equal(root.get("idEmpresa"), filter.getIdEmpresa()));
            }
            if (filter.getFechaPedido() != null) {
                predicates.add(cb.equal(root.get("fechaPedido"), filter.getFechaPedido()));
            }
            if (filter.getEstado() != null) {
                predicates.add(cb.equal(root.get("estado"), filter.getEstado()));
            }
            if (filter.getCosteTotal() != null) {
                predicates.add(cb.equal(root.get("costeTotal"), filter.getCosteTotal()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
