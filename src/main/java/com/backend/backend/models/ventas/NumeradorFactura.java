package com.backend.backend.models.ventas;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "numeradores_factura")
public class NumeradorFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_empresa", nullable = false)
    private Long idEmpresa;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "ultimo_numero", nullable = false)
    private Integer ultimoNumero;
}
