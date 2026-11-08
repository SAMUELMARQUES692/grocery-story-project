package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.repository.ProdutoLojaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProdutoLojaService {

    private final ProdutoLojaRepository produtoLojaRepository;

}
