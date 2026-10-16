package dev.samuel.projectgrocery.controller;

import dev.samuel.projectgrocery.request.FornecedorRequest;
import dev.samuel.projectgrocery.response.FornecedorResponse;
import dev.samuel.projectgrocery.service.FornecedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<FornecedorResponse>> listarFornecedor() {
        return ResponseEntity.ok(fornecedorService.listarFornecedores());
    }

    @PutMapping("/{id}")
    public ResponseEntity<FornecedorResponse> atualizarForncedor(@PathVariable Long id,@RequestBody @Valid FornecedorRequest request) {
        return ResponseEntity.ok(fornecedorService.atualizarFornecedor(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFornecedor(@PathVariable Long id) {
        fornecedorService.deletarFornecedor(id);
        return ResponseEntity.noContent().build();
    }

}
