package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.entity.EstoqueDeposito;
import dev.samuel.projectgrocery.entity.Produto;
import dev.samuel.projectgrocery.exception.EstoqueDepositoDuplicadoException;
import dev.samuel.projectgrocery.exception.ProdutoFracionavelException;
import dev.samuel.projectgrocery.exception.ProdutoInativoException;
import dev.samuel.projectgrocery.exception.ProdutoNotFoundException;
import dev.samuel.projectgrocery.mapper.EstoqueDepositoMapper;
import dev.samuel.projectgrocery.repository.EstoqueDepositoRepository;
import dev.samuel.projectgrocery.repository.ProdutoRepository;
import dev.samuel.projectgrocery.request.EstoqueDepositoRequest;
import dev.samuel.projectgrocery.response.EstoqueDepositoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
