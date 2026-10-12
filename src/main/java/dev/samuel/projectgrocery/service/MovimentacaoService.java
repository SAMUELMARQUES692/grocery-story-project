package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.entity.EstoqueDeposito;
import dev.samuel.projectgrocery.entity.ItemGondola;
import dev.samuel.projectgrocery.entity.Movimentacao;
import dev.samuel.projectgrocery.entity.Produto;
import dev.samuel.projectgrocery.enums.LocalProduto;
import dev.samuel.projectgrocery.enums.TipoMovimentacao;
import dev.samuel.projectgrocery.exception.ProdutoEstoqueDepositoNotFoundException;
import dev.samuel.projectgrocery.exception.ProdutoItemGondolaNotFoundException;
import dev.samuel.projectgrocery.exception.ProdutoNotFoundException;
import dev.samuel.projectgrocery.mapper.MovimentacaoMapper;
import dev.samuel.projectgrocery.repository.EstoqueDepositoRepository;
import dev.samuel.projectgrocery.repository.ItemGondolaRepository;
import dev.samuel.projectgrocery.repository.MovimentacaoRepository;
import dev.samuel.projectgrocery.repository.ProdutoRepository;
import dev.samuel.projectgrocery.request.MovimentacaoRequest;
import dev.samuel.projectgrocery.response.MovimentacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueDepositoRepository estoqueDepositoRepository;
    private final ItemGondolaRepository itemGondolaRepository;
    private final MovimentacaoMapper movimentacaoMapper;

    @Transactional
    public MovimentacaoResponse entradaEstoque(MovimentacaoRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ProdutoNotFoundException(request.produtoId()));

        if (request.local().equals(LocalProduto.DEPOSITO) && request.tipo().equals(TipoMovimentacao.ENTRADA)) {
            EstoqueDeposito estoqueDeposito = estoqueDepositoRepository.findByProdutoId(produto.getId())
                    .orElseThrow(() -> new ProdutoEstoqueDepositoNotFoundException(produto.getId()));

            Integer somaEstoque = estoqueDeposito.getQuantidade() + request.quantidade();

            estoqueDeposito.setQuantidade(somaEstoque);
            estoqueDepositoRepository.save(estoqueDeposito);
        }

        Movimentacao movimentacao = movimentacaoMapper.toMovimentacao(request);
        movimentacao.setProduto(produto);
        Movimentacao salvar = movimentacaoRepository.save(movimentacao);
        return movimentacaoMapper.toMovimentacaoResponse(salvar);
    }

    @Transactional
    public MovimentacaoResponse reposicaoGondola(MovimentacaoRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ProdutoNotFoundException(request.produtoId()));

        if (request.local().equals(LocalProduto.GONDOLA) && request.tipo().equals(TipoMovimentacao.REPOSICAO)) {
            EstoqueDeposito estoqueDeposito = estoqueDepositoRepository.findByProdutoId(produto.getId())
                    .orElseThrow(() -> new ProdutoEstoqueDepositoNotFoundException(produto.getId()));

            ItemGondola itemGondola = itemGondolaRepository.findByProdutoId(produto.getId())
                    .orElseThrow(() -> new ProdutoItemGondolaNotFoundException(produto.getId()));

            Integer subtrairPosicaoEstoque = estoqueDeposito.getQuantidade() - request.quantidade();
            Integer somarItemGondola = itemGondola.getQuantidadeAtual() + request.quantidade();

            estoqueDeposito.setQuantidade(subtrairPosicaoEstoque);
            itemGondola.setQuantidadeAtual(somarItemGondola);

            estoqueDepositoRepository.save(estoqueDeposito);
            itemGondolaRepository.save(itemGondola);
        }

        Movimentacao movimentacao = movimentacaoMapper.toMovimentacao(request);
        movimentacao.setProduto(produto);
        Movimentacao salvar = movimentacaoRepository.save(movimentacao);
        return movimentacaoMapper.toMovimentacaoResponse(salvar);
    }

    @Transactional
    public MovimentacaoResponse venda(MovimentacaoRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ProdutoNotFoundException(request.produtoId()));

        if (request.local().equals(LocalProduto.GONDOLA) && request.tipo().equals(TipoMovimentacao.VENDA)) {
            ItemGondola itemGondola = itemGondolaRepository.findByProdutoId(produto.getId())
                    .orElseThrow(() -> new ProdutoItemGondolaNotFoundException(produto.getId()));

            Integer subtrairGondola = itemGondola.getQuantidadeAtual() - request.quantidade();
            itemGondola.setQuantidadeAtual(subtrairGondola);
            itemGondolaRepository.save(itemGondola);
        }

        Movimentacao movimentacao = movimentacaoMapper.toMovimentacao(request);
        movimentacao.setProduto(produto);
        Movimentacao salvar = movimentacaoRepository.save(movimentacao);
        return movimentacaoMapper.toMovimentacaoResponse(salvar);
    }

    @Transactional
    public MovimentacaoResponse ajusteEstoque(MovimentacaoRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ProdutoNotFoundException(request.produtoId()));

        if (request.local().equals(LocalProduto.DEPOSITO) && request.tipo().equals(TipoMovimentacao.AJUSTE_ENTRADA)) {
            EstoqueDeposito estoqueDeposito = estoqueDepositoRepository.findByProdutoId(produto.getId())
                    .orElseThrow(() -> new ProdutoEstoqueDepositoNotFoundException(produto.getId()));

            Integer somarEntrada = estoqueDeposito.getQuantidade() + request.quantidade();
            estoqueDeposito.setQuantidade(somarEntrada);
            estoqueDepositoRepository.save(estoqueDeposito);
        }

        if (request.local().equals(LocalProduto.DEPOSITO) && request.tipo().equals(TipoMovimentacao.AJUSTE_SAIDA)) {
            EstoqueDeposito estoqueDeposito = estoqueDepositoRepository.findByProdutoId(produto.getId())
                    .orElseThrow(() -> new ProdutoEstoqueDepositoNotFoundException(produto.getId()));

            Integer somarEntrada = estoqueDeposito.getQuantidade() - request.quantidade();
            estoqueDeposito.setQuantidade(somarEntrada);
            estoqueDepositoRepository.save(estoqueDeposito);
        }

        Movimentacao movimentacao = movimentacaoMapper.toMovimentacao(request);
        movimentacao.setProduto(produto);
        Movimentacao salvar = movimentacaoRepository.save(movimentacao);
        return movimentacaoMapper.toMovimentacaoResponse(salvar);
    }

    @Transactional
    public MovimentacaoResponse perdaEstoque(MovimentacaoRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ProdutoNotFoundException(request.produtoId()));

        if (request.local().equals(LocalProduto.DEPOSITO) && request.tipo().equals(TipoMovimentacao.PERDA)) {
            EstoqueDeposito estoqueDeposito = estoqueDepositoRepository.findByProdutoId(produto.getId())
                    .orElseThrow(() -> new ProdutoEstoqueDepositoNotFoundException(produto.getId()));

            Integer perdaEstoque = estoqueDeposito.getQuantidade() - request.quantidade();
            estoqueDeposito.setQuantidade(perdaEstoque);
            estoqueDepositoRepository.save(estoqueDeposito);
        }

        Movimentacao movimentacao = movimentacaoMapper.toMovimentacao(request);
        movimentacao.setProduto(produto);
        Movimentacao salvar = movimentacaoRepository.save(movimentacao);
        return movimentacaoMapper.toMovimentacaoResponse(salvar);
    }
}
