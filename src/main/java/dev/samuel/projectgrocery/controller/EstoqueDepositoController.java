package dev.samuel.projectgrocery.controller;

import dev.samuel.projectgrocery.request.EstoqueDepositoRequest;
import dev.samuel.projectgrocery.response.EstoqueDepositoResponse;
import dev.samuel.projectgrocery.service.EstoqueDepositoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/estoque-deposito")
public class EstoqueDepositoController {

    private final EstoqueDepositoService estoqueDepositoService;

    @PostMapping
    public ResponseEntity<EstoqueDepositoResponse> criarEstoqueDeposito(@RequestBody @Valid EstoqueDepositoRequest request) {
        EstoqueDepositoResponse response = estoqueDepositoService.criarEstoqueDeposito(request);
        URI location = URI.create("/api/estoque-deposito/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EstoqueDepositoResponse>> listarEstoque() {
        return ResponseEntity.ok(estoqueDepositoService.listasrEstoque());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstoqueDepositoResponse> atualizarEstoque(@PathVariable Long id, @RequestBody @Valid EstoqueDepositoRequest request) {
        return ResponseEntity.ok(estoqueDepositoService.atualizarEstoque(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEstoque(@PathVariable Long id) {
        estoqueDepositoService.deletarEstoque(id);
        return ResponseEntity.noContent().build();
    }


}
