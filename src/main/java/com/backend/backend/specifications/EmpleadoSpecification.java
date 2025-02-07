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
            if (filter.getTelefono() != null && !filter.getTelefono().isEmpty()) {
                predicates.add(cb.like(root.get("telefono"), "%" + filter.getTelefono() + "%"));
            }
            if (filter.getDireccion() != null && !filter.getDireccion().isEmpty()) {
                predicates.add(cb.like(cb.lower(root.get("direccion")), "%" + filter.getDireccion().toLowerCase() + "%"));
            }
            if (filter.getFechaNacimiento() != null && !filter.getFechaNacimiento().isEmpty()) {
                predicates.add(cb.equal(root.get("fechaNacimiento"), filter.getFechaNacimiento()));
            }
            if (filter.getGenero() != null && !filter.getGenero().isEmpty()) {
                predicates.add(cb.equal(root.get("genero"), filter.getGenero()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
