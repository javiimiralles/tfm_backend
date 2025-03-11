package com.backend.backend.specifications;

import com.backend.backend.filters.PedidoFilter;
import com.backend.backend.models.ventas.Pedido;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PedidoSpecification {

    public static Specification<Pedido> withFilters(PedidoFilter filter) {
        return (Root<Pedido> root, CriteriaQuery<?> query, CriteriaBuilder cb)  -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getIdCliente() != null) {
                predicates.add(cb.equal(root.get("cliente").get("id"), filter.getIdCliente()));
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
            if (filter.getTipo() != null) {
                predicates.add(cb.equal(root.get("tipo"), filter.getTipo()));
            }
            if (filter.getMetodoPago() != null) {
                predicates.add(cb.equal(root.get("metodoPago"), filter.getMetodoPago()));
            }
            if (filter.getCosteTotal() != null) {
                predicates.add(cb.equal(root.get("costeTotal"), filter.getCosteTotal()));
            }
            if (filter.getObservaciones() != null && !filter.getObservaciones().trim().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("observaciones")), "%" + filter.getObservaciones().toLowerCase() + "%"));
            }

            if (filter.getQuery() != null && !filter.getQuery().isEmpty()) {
                String queryText = "%" + filter.getQuery().toLowerCase() + "%";
                Predicate searchPredicate = cb.or(
                        cb.like(cb.lower(root.get("cliente").get("nombre")), queryText),
                        cb.like(cb.lower(root.get("cliente").get("apellidos")), queryText),
                        cb.like(cb.lower(root.get("cliente").get("email")), queryText),
                        cb.like(cb.lower(root.get("observaciones")), queryText)
                );
                predicates.add(searchPredicate);
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
