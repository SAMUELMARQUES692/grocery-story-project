package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.repository.ProdutoImagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProdutoImagemService {

    private final ProdutoImagemRepository produtoImagemRepository;



}
