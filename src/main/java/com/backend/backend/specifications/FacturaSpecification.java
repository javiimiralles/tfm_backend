package com.backend.backend.specifications;

import com.backend.backend.filters.FacturaFilter;
import com.backend.backend.models.ventas.Factura;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FacturaSpecification {

    public static Specification<Factura> withFilters(FacturaFilter filter) {
        return (Root<Factura> root, CriteriaQuery<?> query, CriteriaBuilder cb)  -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getIdPedido() != null) {
                predicates.add(cb.equal(root.get("pedido").get("id"), filter.getIdPedido()));
            }
            if (filter.getIdEmpresa() != null) {
                predicates.add(cb.equal(root.get("idEmpresa"), filter.getIdEmpresa()));
            }
            if (filter.getIdCliente() != null) {
                predicates.add(cb.equal(root.get("cliente").get("id"), filter.getIdCliente()));
            }
            if (filter.getFechaFactura() != null) {
                predicates.add(cb.equal(root.get("fechaFactura"), filter.getFechaFactura()));
            }
            if (filter.getImporte() != null) {
                predicates.add(cb.equal(root.get("importe"), filter.getImporte()));
            }
            if (filter.getNumeroFactura() != null && !filter.getNumeroFactura().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("numeroFactura")), "%" + filter.getNumeroFactura().toLowerCase() + "%"));
            }

            if (filter.getQuery() != null && !filter.getQuery().isEmpty()) {
                String queryText = "%" + filter.getQuery().toLowerCase() + "%";
                Predicate searchPredicate = cb.or(
                        cb.like(cb.lower(root.get("pedido").get("cliente").get("nombre")), queryText),
                        cb.like(cb.lower(root.get("pedido").get("cliente").get("apellidos")), queryText),
                        cb.like(cb.lower(root.get("pedido").get("cliente").get("email")), queryText),
                        cb.like(cb.lower(root.get("numeroFactura")), queryText)
                );
                predicates.add(searchPredicate);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
