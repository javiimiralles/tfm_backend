package com.backend.backend.repository;

import com.backend.backend.models.paises.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaisRepository extends JpaRepository<Pais, Long> {
}
