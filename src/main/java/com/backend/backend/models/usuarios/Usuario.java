package com.backend.backend.models.usuarios;

import com.backend.backend.models.roles.Rol;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 150, unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", referencedColumnName = "id", nullable = false)
    private Rol rol;

    public Set<String> getAccionesPermitidas() {
        return this.rol.getPermisos().stream()
                .map(permiso -> permiso.getAccion().getAccion())
                .collect(Collectors.toSet());
    }
}
