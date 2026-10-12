package dev.samuel.projectgrocery.controller;

import dev.samuel.projectgrocery.request.EstoqueDepositoRequest;
import dev.samuel.projectgrocery.response.EstoqueDepositoResponse;
import dev.samuel.projectgrocery.service.EstoqueDepositoService;
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
@RequestMapping("/api/estoque-deposito")
public class EstoqueDepositoController {

    private final EstoqueDepositoService estoqueDepositoService;

    @PostMapping
    public ResponseEntity<EstoqueDepositoResponse> criarEstoqueDeposito(@RequestBody @Valid EstoqueDepositoRequest request) {
        EstoqueDepositoResponse response = estoqueDepositoService.criarEstoqueDeposito(request);
        URI location = URI.create("/api/estoque-deposito/" + response.id());
        return ResponseEntity.created(location).body(response);
    }
}
