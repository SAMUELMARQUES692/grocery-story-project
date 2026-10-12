package dev.samuel.projectgrocery.controller;

import dev.samuel.projectgrocery.request.ItemGondolaRequest;
import dev.samuel.projectgrocery.response.ItemGondolaResponse;
import dev.samuel.projectgrocery.service.ItemGondolaService;
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
@RequestMapping("/api/item-gondola")
public class ItemGondolaController {

    private final ItemGondolaService itemGondolaService;

    @PostMapping
    public ResponseEntity<ItemGondolaResponse> criarItemGondola(@RequestBody @Valid ItemGondolaRequest request) {
        ItemGondolaResponse response = itemGondolaService.criarItemGondola(request);
        URI location = URI.create("/api/item-gondola/" + response.id());
        return ResponseEntity.created(location).body(response);
    }

}
