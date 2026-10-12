package dev.samuel.projectgrocery.controller;

import dev.samuel.projectgrocery.request.GondolaRequest;
import dev.samuel.projectgrocery.response.GondolaResponse;
import dev.samuel.projectgrocery.service.GondolaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/gondola")
public class GondolaController {

    private final GondolaService gondolaService;

    @PostMapping
    public ResponseEntity<GondolaResponse> criarGondola(@RequestBody @Valid GondolaRequest request) {
        GondolaResponse response = gondolaService.criarGondola(request);
        URI location = URI.create("/api/gondola/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<GondolaResponse>> listarGondolas() {
        return ResponseEntity.ok(gondolaService.listarGondolas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GondolaResponse> atualizarGondola(@PathVariable Long id, @RequestBody @Valid GondolaRequest request) {
        return ResponseEntity.ok(gondolaService.atualizarGondola(id, request));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarGondola(@PathVariable Long id) {
        gondolaService.deletarGondola(id);
        return ResponseEntity.noContent().build();
    }

}
