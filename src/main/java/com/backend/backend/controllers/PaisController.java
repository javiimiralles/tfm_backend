package com.backend.backend.controllers;

import com.backend.backend.dto.HttpResponse;
import com.backend.backend.models.Pais;
import com.backend.backend.services.PaisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/paises")
public class PaisController {

    private final PaisService paisService;

    public PaisController(PaisService paisService) {
        this.paisService = paisService;
    }

    @GetMapping("")
    public ResponseEntity<Object> getPaises() {
        List<Pais> paises = paisService.findPaises();
        return ResponseEntity.ok(new HttpResponse(true, "Paises obtenidos correctamente", paises));
    }

}
