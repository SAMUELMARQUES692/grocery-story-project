package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.entity.Produto;
import dev.samuel.projectgrocery.entity.ProdutoLoja;
import dev.samuel.projectgrocery.exception.*;
import dev.samuel.projectgrocery.mapper.ProdutoLojaMapper;
import dev.samuel.projectgrocery.repository.ProdutoLojaRepository;
import dev.samuel.projectgrocery.repository.ProdutoRepository;
import dev.samuel.projectgrocery.request.AtualizarVisibilidadeRequest;
import dev.samuel.projectgrocery.request.ProdutoLojaRequest;
import dev.samuel.projectgrocery.response.ProdutoLojaResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProdutoLojaService {

    private final ProdutoLojaRepository produtoLojaRepository;
    private final ProdutoRepository produtoRepository;
    private final ProdutoLojaMapper produtoLojaMapper;

    @Transactional
    public ProdutoLojaResponse cadastrar(Long produtoId, ProdutoLojaRequest request) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ProdutoNotFoundException(produtoId));

        if (!produto.isAtivo()) {
            throw new ProdutoInativoException(produto.getId());
        }

        if (produtoLojaRepository.existsByProdutoId(produto.getId())) {
            throw new ProdutoJaPublicadoException(produto.getId());
        }

        if (request.precoPromocional() != null && request.precoPromocional().compareTo(produto.getPrecoVenda()) >= 0) {
            throw new PrecoPromocionalMaiorQuePrecoVendaException(request.precoPromocional(), produto.getPrecoVenda());
        }

        ProdutoLoja produtoLoja = produtoLojaMapper.toProdutoLoja(request);
        produtoLoja.setProduto(produto);
        produtoLoja.setVisivel(true);
        ProdutoLoja salvar = produtoLojaRepository.save(produtoLoja);
        return produtoLojaMapper.toProdutoLojaResponse(salvar);
    }

    public List<ProdutoLojaResponse> listar() {
        return produtoLojaRepository.findAll().stream()
                .map(produtoLojaMapper::toProdutoLojaResponse)
                .toList();
    }

    @Transactional
    public ProdutoLojaResponse atualizar(Long produtoId, ProdutoLojaRequest request) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ProdutoNotFoundException(produtoId));

        ProdutoLoja produtoLoja = produtoLojaRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new ProdutoLojaNotFoundException(produtoId));

        if (request.precoPromocional() != null && request.precoPromocional().compareTo(produto.getPrecoVenda()) >= 0) {
            throw new PrecoPromocionalMaiorQuePrecoVendaException(request.precoPromocional(), produto.getPrecoVenda());
        }

        produtoLojaMapper.atualizarProdutoLoja(request, produtoLoja);

        ProdutoLoja salvar = produtoLojaRepository.save(produtoLoja);
        return produtoLojaMapper.toProdutoLojaResponse(salvar);
    }

    public ProdutoLojaResponse buscarProdutoLojaPorProdutoId(Long produtoId) {
        ProdutoLoja produtoLoja = produtoLojaRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new ProdutoLojaNotFoundException(produtoId));
        return produtoLojaMapper.toProdutoLojaResponse(produtoLoja);
    }

    @Transactional
    public void deletar(Long produtoId) {
        ProdutoLoja produtoLoja = produtoLojaRepository.findByProdutoId(produtoId)
                .orElseThrow(() -> new ProdutoLojaNotFoundException(produtoId));
        produtoLojaRepository.delete(produtoLoja);
    }

    @Transactional
    public ProdutoLojaResponse atualizarVisibilidade(Long produtoId, AtualizarVisibilidadeRequest request) {
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ProdutoNotFoundException(produtoId));

       ProdutoLoja produtoLoja = produtoLojaRepository.findByProdutoId(produtoId)
               .orElseThrow(() -> new ProdutoLojaNotFoundException(produtoId));

        if (!produto.isAtivo() && request.visibilidade()) {
            throw new ProdutoInativoException(produtoId);
        }

       produtoLoja.setVisivel(request.visibilidade());
       produtoLojaRepository.save(produtoLoja);

       return produtoLojaMapper.toProdutoLojaResponse(produtoLoja);
    }
    
}
