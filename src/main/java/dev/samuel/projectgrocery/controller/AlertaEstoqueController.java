package dev.samuel.projectgrocery.controller;

import dev.samuel.projectgrocery.enums.StatusAlerta;
import dev.samuel.projectgrocery.response.AlertaEstoqueResponse;
import dev.samuel.projectgrocery.service.AlertaEstoqueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/alerta-estoque")
public class AlertaEstoqueController {

    private final AlertaEstoqueService alertaEstoqueService;

    @GetMapping
    public ResponseEntity<List<AlertaEstoqueResponse>> listar(@RequestParam(required = false) StatusAlerta statusAlerta) {
        return ResponseEntity.ok(alertaEstoqueService.listar(statusAlerta));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaEstoqueResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alertaEstoqueService.buscarPorId(id));
    }
}
