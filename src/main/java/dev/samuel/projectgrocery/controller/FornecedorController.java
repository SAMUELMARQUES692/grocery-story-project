package dev.samuel.projectgrocery.controller;

import dev.samuel.projectgrocery.request.FornecedorRequest;
import dev.samuel.projectgrocery.response.FornecedorResponse;
import dev.samuel.projectgrocery.service.FornecedorService;
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
@RequestMapping("/api/fornecedores")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @PostMapping
    public ResponseEntity<FornecedorResponse> criarFornecedor(@RequestBody @Valid FornecedorRequest request) {
        FornecedorResponse response = fornecedorService.criarFornecedor(request);
        URI location = URI.create("/api/fornecedores/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

}
