package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.entity.EstoqueDeposito;
import dev.samuel.projectgrocery.entity.Produto;
import dev.samuel.projectgrocery.exception.*;
import dev.samuel.projectgrocery.mapper.EstoqueDepositoMapper;
import dev.samuel.projectgrocery.repository.EstoqueDepositoRepository;
import dev.samuel.projectgrocery.repository.ProdutoRepository;
import dev.samuel.projectgrocery.request.EstoqueDepositoRequest;
import dev.samuel.projectgrocery.response.EstoqueDepositoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EstoqueDepositoService {

    private final EstoqueDepositoRepository estoqueDepositoRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueDepositoMapper estoqueDepositoMapper;

    @Transactional
    public EstoqueDepositoResponse criarEstoqueDeposito(EstoqueDepositoRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ProdutoNotFoundException(request.produtoId()));

        if (!produto.isAtivo()) {
            throw new ProdutoInativoException(produto.getId());
        }

        if (produto.getUnidadeMedida().isFracionavel()) {
            throw new ProdutoFracionavelException(produto.getUnidadeMedida());
        }

        if (estoqueDepositoRepository.existsByProdutoId(produto.getId())) {
            throw new EstoqueDepositoDuplicadoException(produto.getId());
        }

        EstoqueDeposito estoqueDeposito = estoqueDepositoMapper.toEstoqueDeposito(request);
        estoqueDeposito.setProduto(produto);
        EstoqueDeposito salvar = estoqueDepositoRepository.save(estoqueDeposito);
        return estoqueDepositoMapper.toEstoqueDepositoResponse(salvar);
    }

    public List<EstoqueDepositoResponse> listasrEstoque() {
        return estoqueDepositoRepository.findAll().stream()
                .map(estoqueDepositoMapper::toEstoqueDepositoResponse)
                .toList();
    }

    public EstoqueDepositoResponse atualizarEstoque(Long id, EstoqueDepositoRequest request) {
        EstoqueDeposito estoqueDeposito = estoqueDepositoRepository.findById(id)
                .orElseThrow(() -> new EstoqueDepositoNotFoundException(id));

        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ProdutoNotFoundException(request.produtoId()));

        if (produto.getUnidadeMedida().isFracionavel()) {
            throw new ProdutoFracionavelException(produto.getUnidadeMedida());
        }

        estoqueDepositoMapper.atualizarEstoqueDeposito(request, estoqueDeposito);
        estoqueDeposito.setProduto(produto);
        EstoqueDeposito salvar = estoqueDepositoRepository.save(estoqueDeposito);
        return estoqueDepositoMapper.toEstoqueDepositoResponse(salvar);
    }

    public void deletarEstoque(Long id) {
        EstoqueDeposito estoqueDeposito = estoqueDepositoRepository.findById(id)
                .orElseThrow(() -> new EstoqueDepositoNotFoundException(id));
        estoqueDepositoRepository.delete(estoqueDeposito);
    }

}
