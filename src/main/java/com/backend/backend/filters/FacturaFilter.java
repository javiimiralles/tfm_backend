package com.backend.backend.filters;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FacturaFilter {

    private Long idPedido;

    private Long idEmpresa;

    private Long idCliente;

    private Date fechaFactura;

    private BigDecimal importe;

    private String numeroFactura;

    private String query;

    // Paginacion
    private Integer page;

    private Integer size;

    private String sortBy;

    private String sortDirection;

    public Pageable getPageable() {
        int page = this.page == null ? 0 : this.page;
        int size = this.size == null ? 10 : this.size;
        String sortBy = this.sortBy == null ? "fechaFactura" : this.sortBy;
        String sortDirection = this.sortDirection == null ? "DESC" : this.sortDirection;
        return PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
    }
}
