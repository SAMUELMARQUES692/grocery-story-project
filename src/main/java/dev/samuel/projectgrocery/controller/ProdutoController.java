package dev.samuel.projectgrocery.controller;

import dev.samuel.projectgrocery.request.ProdutoRequest;
import dev.samuel.projectgrocery.response.ProdutoResponse;
import dev.samuel.projectgrocery.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

   private final ProdutoService produtoService;

   @PostMapping
   public ResponseEntity<ProdutoResponse> criarProduto(@RequestBody @Valid ProdutoRequest request) {
       ProdutoResponse response = produtoService.criarProduto(request);
       URI location = URI.create("/api/produtos/" + response.id());
       return ResponseEntity.created(location).body(response);
   }

   @GetMapping
   public ResponseEntity<List<ProdutoResponse>> listarProdutos() {
       return ResponseEntity.ok(produtoService.listarProdutos());
   }

   @PutMapping("/{id}")
   public ResponseEntity<ProdutoResponse> atualizarProduto(@PathVariable Long id, @RequestBody @Valid ProdutoRequest request) {
       return ResponseEntity.ok(produtoService.atualizarProduto(id, request));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
       produtoService.deletarProduto(id);
       return ResponseEntity.noContent().build();
   }
}
