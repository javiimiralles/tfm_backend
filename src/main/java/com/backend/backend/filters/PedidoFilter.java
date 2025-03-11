package com.backend.backend.filters;

import com.backend.backend.enums.EstadoPedidoEnum;
import com.backend.backend.enums.MetodoPagoEnum;
import com.backend.backend.enums.TipoPedidoEnum;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PedidoFilter {

    private Long idCliente;

    private Long idEmpresa;

    private Date fechaPedido;

    private EstadoPedidoEnum estado;

    private TipoPedidoEnum tipo;

    private MetodoPagoEnum metodoPago;

    private BigDecimal costeTotal;

    private String observaciones;

    private String query;

    // Paginacion
    private Integer page;

    private Integer size;

    private String sortBy;

    private String sortDirection;

    public Pageable getPageable() {
        int page = this.page == null ? 0 : this.page;
        int size = this.size == null ? 10 : this.size;
        String sortBy = this.sortBy == null ? "fechaPedido" : this.sortBy;
        String sortDirection = this.sortDirection == null ? "DESC" : this.sortDirection;
        return PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
    }
}
