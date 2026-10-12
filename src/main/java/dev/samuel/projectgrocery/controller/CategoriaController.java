package dev.samuel.projectgrocery.controller;

import dev.samuel.projectgrocery.request.CategoriaRequest;
import dev.samuel.projectgrocery.response.CategoriaResponse;
import dev.samuel.projectgrocery.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    public ResponseEntity<CategoriaResponse> criarCategoria(@RequestBody @Valid CategoriaRequest request) {
        CategoriaResponse response = categoriaService.criarCategoria(request);
        URI location = URI.create("/api/categorias/" + response.id());
        return ResponseEntity.created(location).body(response);
    }
}
