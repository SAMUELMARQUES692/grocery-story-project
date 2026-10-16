package dev.samuel.projectgrocery.controller;

import dev.samuel.projectgrocery.request.ItemGondolaRequest;
import dev.samuel.projectgrocery.response.ItemGondolaResponse;
import dev.samuel.projectgrocery.service.ItemGondolaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/item-gondola")
public class ItemGondolaController {

    private final ItemGondolaService itemGondolaService;

    @PostMapping
    public ResponseEntity<ItemGondolaResponse> criarItemGondola(@RequestBody @Valid ItemGondolaRequest request) {
        ItemGondolaResponse response = itemGondolaService.criarItemGondola(request);
        URI location = URI.create("/api/item-gondola/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ItemGondolaResponse>> listarItemgondola() {
        return ResponseEntity.ok(itemGondolaService.listarItemGondola());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemGondolaResponse> atualizarItemGondola(@PathVariable Long id, @RequestBody @Valid ItemGondolaRequest request) {
        return ResponseEntity.ok(itemGondolaService.atualizarItemGondola(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItemGondola(@PathVariable Long id) {
        itemGondolaService.deletarItemGondola(id);
        return ResponseEntity.noContent().build();
    }

}
