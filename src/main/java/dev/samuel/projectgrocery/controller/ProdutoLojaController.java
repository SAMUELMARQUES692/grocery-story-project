package dev.samuel.projectgrocery.controller;

import dev.samuel.projectgrocery.service.ProdutoLojaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/loja/produto")
@RestController
public class ProdutoLojaController {

    private final ProdutoLojaService produtoLojaService;



}
