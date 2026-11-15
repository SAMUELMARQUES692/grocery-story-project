package dev.samuel.projectgrocery.controller;

import dev.samuel.projectgrocery.request.AtualizarVisibilidadeRequest;
import dev.samuel.projectgrocery.request.ProdutoLojaRequest;
import dev.samuel.projectgrocery.response.ProdutoLojaResponse;
import dev.samuel.projectgrocery.service.ProdutoLojaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/admin/produtos/{produtoId}/loja")
@RestController
public class ProdutoLojaController {

    private final ProdutoLojaService produtoLojaService;

    @PostMapping
    public ResponseEntity<ProdutoLojaResponse> cadastrar(@PathVariable Long produtoId, @RequestBody @Valid ProdutoLojaRequest request) {
        ProdutoLojaResponse response = produtoLojaService.cadastrar(produtoId, request);
        URI location = URI.create("/api/admin/produtos/" + response.produtoId() + "/loja");
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/todos")
    public ResponseEntity<List<ProdutoLojaResponse>> listar() {
        return ResponseEntity.ok(produtoLojaService.listar());
    }

    @PutMapping
    public ResponseEntity<ProdutoLojaResponse> atualizar(@PathVariable Long produtoId, @RequestBody @Valid ProdutoLojaRequest request) {
        return ResponseEntity.ok(produtoLojaService.atualizar(produtoId, request));
    }

    @GetMapping
    public ResponseEntity<ProdutoLojaResponse> buscarProdutoLojaPorProdutoId(@PathVariable Long produtoId) {
        return ResponseEntity.ok(produtoLojaService.buscarProdutoLojaPorProdutoId(produtoId));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletar(@PathVariable Long produtoId) {
        produtoLojaService.deletar(produtoId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/visibilidade")
    public ResponseEntity<ProdutoLojaResponse> atualizarVisibilidade(@PathVariable Long produtoId, @RequestBody @Valid AtualizarVisibilidadeRequest request) {
        return ResponseEntity.ok(produtoLojaService.atualizarVisibilidade(produtoId, request));
    }


}
