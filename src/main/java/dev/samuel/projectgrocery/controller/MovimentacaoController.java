package dev.samuel.projectgrocery.controller;

import dev.samuel.projectgrocery.request.*;
import dev.samuel.projectgrocery.response.MovimentacaoResponse;
import dev.samuel.projectgrocery.service.MovimentacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/movimentacoes")
public class MovimentacaoController {

    private final MovimentacaoService movimentacaoService;

    @PostMapping("/entrada")
    public ResponseEntity<MovimentacaoResponse> entradaEstoque(@RequestBody @Valid EntradaEstoqueRequest request) {
        MovimentacaoResponse response = movimentacaoService.entradaEstoque(request);
        URI location = URI.create("/api/movimentacoes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/reposicao")
    public ResponseEntity<List<MovimentacaoResponse>> reposicaoGondola(@RequestBody @Valid ReposicaoRequest request) {
        List<MovimentacaoResponse> response = movimentacaoService.reposicaoGondola(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/venda")
    public ResponseEntity<MovimentacaoResponse> venda(@RequestBody @Valid VendaRequest request) {
        MovimentacaoResponse response = movimentacaoService.venda(request);
        URI location = URI.create("/api/movimentacoes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/ajuste")
    public ResponseEntity<MovimentacaoResponse> ajusteEstoque(@RequestBody @Valid AjusteRequest request) {
        MovimentacaoResponse response = movimentacaoService.ajusteEstoque(request);
        URI location = URI.create("/api/movimentacoes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @PostMapping("/perda")
    public ResponseEntity<MovimentacaoResponse> perdaEstoque(@RequestBody @Valid PerdaRequest request) {
        MovimentacaoResponse response = movimentacaoService.perdaEstoque(request);
        URI location = URI.create("/api/movimentacoes/" + response.id());
        return ResponseEntity.created(location).body(response);
    }
}
