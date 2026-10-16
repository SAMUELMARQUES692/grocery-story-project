package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.entity.Gondola;
import dev.samuel.projectgrocery.entity.ItemGondola;
import dev.samuel.projectgrocery.entity.Produto;
import dev.samuel.projectgrocery.exception.GondolaNotFoundException;
import dev.samuel.projectgrocery.exception.ItemGondolaNotFoundException;
import dev.samuel.projectgrocery.exception.ProdutoNotFoundException;
import dev.samuel.projectgrocery.mapper.ItemGondolaMapper;
import dev.samuel.projectgrocery.repository.GondolaRepository;
import dev.samuel.projectgrocery.repository.ItemGondolaRepository;
import dev.samuel.projectgrocery.repository.ProdutoRepository;
import dev.samuel.projectgrocery.request.ItemGondolaRequest;
import dev.samuel.projectgrocery.response.ItemGondolaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemGondolaService {

    private final ItemGondolaRepository itemGondolaRepository;
    private final GondolaRepository gondolaRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemGondolaMapper itemGondolaMapper;

    @Transactional
    public ItemGondolaResponse criarItemGondola(ItemGondolaRequest request) {
        Gondola gondola = gondolaRepository.findById(request.gondolaId())
                .orElseThrow(() -> new GondolaNotFoundException(request.gondolaId()));

        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ProdutoNotFoundException(request.produtoId()));

        ItemGondola itemGondola = itemGondolaMapper.toItemGondola(request);
        itemGondola.setGondola(gondola);
        itemGondola.setProduto(produto);
        ItemGondola salvar = itemGondolaRepository.save(itemGondola);
        return itemGondolaMapper.toItemGondolaResponse(salvar);
    }

    public List<ItemGondolaResponse> listarItemGondola() {
        return itemGondolaRepository.findAll().stream()
                .map(itemGondolaMapper::toItemGondolaResponse)
                .toList();
    }

    public ItemGondolaResponse atualizarItemGondola(Long id, ItemGondolaRequest request) {
        ItemGondola itemGondola = itemGondolaRepository.findById(id)
                .orElseThrow(() -> new ItemGondolaNotFoundException(id));

        itemGondolaMapper.atualizarItemGondola(request, itemGondola);
        ItemGondola salvar = itemGondolaRepository.save(itemGondola);
        return itemGondolaMapper.toItemGondolaResponse(salvar);
    }

    public void deletarItemGondola(Long id) {
        ItemGondola itemGondola = itemGondolaRepository.findById(id)
                .orElseThrow(() -> new ItemGondolaNotFoundException(id));
        itemGondolaRepository.delete(itemGondola);
    }

}
