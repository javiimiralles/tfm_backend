package com.backend.backend.filters;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;

@Data
public class ClienteFilter {

    private Long id;

    private Long idEmpresa;

    private String nombre;

    private String apellidos;

    private String email;

    private Date fechaAlta;

    private String query;

    // Paginacion
    private Integer page;

    private Integer size;

    private String sortBy;

    private String sortDirection;

    public Pageable getPageable() {
        Integer page = this.page == null ? 0 : this.page;
        Integer size = this.size == null ? 10 : this.size;
        String sortBy = this.sortBy == null ? "nombre" : this.sortBy;
        String sortDirection = this.sortDirection == null ? "ASC" : this.sortDirection;
        return PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
    }
}
