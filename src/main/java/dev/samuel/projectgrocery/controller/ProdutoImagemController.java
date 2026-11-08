package dev.samuel.projectgrocery.controller;

import dev.samuel.projectgrocery.service.ProdutoImagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/loja/produto-imagem")
@RestController
public class ProdutoImagemController {

    private final ProdutoImagemService produtoImagemService;



}
