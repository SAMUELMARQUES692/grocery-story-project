package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.entity.Categoria;
import dev.samuel.projectgrocery.exception.CategoriaDuplicadaException;
import dev.samuel.projectgrocery.mapper.CategoriaMapper;
import dev.samuel.projectgrocery.repository.CategoriaRepository;
import dev.samuel.projectgrocery.request.CategoriaRequest;
import dev.samuel.projectgrocery.response.CategoriaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Transactional
    public CategoriaResponse criarCategoria(CategoriaRequest request) {
        String nome = request.nome().trim();

        if (categoriaRepository.existsByNomeIgnoreCase(nome)) {
            throw new CategoriaDuplicadaException(nome);
        }

        Categoria categoria = categoriaMapper.toCategoria(request);
        categoria.setNome(nome);
        Categoria salvar = categoriaRepository.save(categoria);
        return categoriaMapper.toCategoriaResponse(salvar);
    }
}
