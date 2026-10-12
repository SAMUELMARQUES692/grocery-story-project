package dev.samuel.projectgrocery.controller;

import dev.samuel.projectgrocery.request.MovimentacaoRequest;
import dev.samuel.projectgrocery.response.MovimentacaoResponse;
import dev.samuel.projectgrocery.service.MovimentacaoService;
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
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {

    public final MovimentacaoService movimentacaoService;

    @PostMapping("/entrada")
    public ResponseEntity<MovimentacaoResponse> entradaEstoque(@RequestBody @Valid MovimentacaoRequest request) {
        MovimentacaoResponse response = movimentacaoService.entradaEstoque(request);
        URI location = URI.create("/api/movimentacoes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/reposicao")
    public ResponseEntity<MovimentacaoResponse> reposicaoGondola(@RequestBody @Valid MovimentacaoRequest request) {
        MovimentacaoResponse response = movimentacaoService.reposicaoGondola(request);
        URI location = URI.create("/api/movimentacoes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/venda")
    public ResponseEntity<MovimentacaoResponse> venda(@RequestBody @Valid MovimentacaoRequest request) {
        MovimentacaoResponse response = movimentacaoService.venda(request);
        URI location = URI.create("/api/movimentacoes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/ajuste")
    public ResponseEntity<MovimentacaoResponse> ajusteEstoque(@RequestBody @Valid MovimentacaoRequest request) {
        MovimentacaoResponse response = movimentacaoService.ajusteEstoque(request);
        URI location = URI.create("/api/movimentacoes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/perda")
    public ResponseEntity<MovimentacaoResponse> perdaEstoque(@RequestBody @Valid MovimentacaoRequest request) {
        MovimentacaoResponse response = movimentacaoService.perdaEstoque(request);
        URI location = URI.create("/api/movimentacoes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }
}
