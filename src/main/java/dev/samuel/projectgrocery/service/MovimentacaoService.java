package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.entity.*;
import dev.samuel.projectgrocery.enums.LocalProduto;
import dev.samuel.projectgrocery.enums.TipoMovimentacao;
import dev.samuel.projectgrocery.exception.*;
import dev.samuel.projectgrocery.mapper.MovimentacaoMapper;
import dev.samuel.projectgrocery.repository.EstoqueDepositoRepository;
import dev.samuel.projectgrocery.repository.ItemGondolaRepository;
import dev.samuel.projectgrocery.repository.MovimentacaoRepository;
import dev.samuel.projectgrocery.repository.ProdutoRepository;
import dev.samuel.projectgrocery.request.EntradaEstoqueRequest;
import dev.samuel.projectgrocery.request.MovimentacaoRequest;
import dev.samuel.projectgrocery.request.ReposicaoRequest;
import dev.samuel.projectgrocery.response.MovimentacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class MovimentacaoService {

    private final MovimentacaoRepository movimentacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueDepositoRepository estoqueDepositoRepository;
    private final ItemGondolaRepository itemGondolaRepository;
    private final MovimentacaoMapper movimentacaoMapper;
    private final AlertaEstoqueService alertaEstoqueService;

    @Transactional
    public MovimentacaoResponse entradaEstoque(EntradaEstoqueRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ProdutoNotFoundException(request.produtoId()));

        // 1. Verifica se o produto esta ativo
        if (!produto.isAtivo()) {
            throw new ProdutoInativoException(produto.getId());
        }

        // 2. busca a posicão no estoque que tem relacionamento com o produto
        EstoqueDeposito estoqueDeposito = estoqueDepositoRepository.findByProdutoId(request.produtoId())
                .orElseThrow(() -> new ProdutoEstoqueDepositoNotFoundException(request.produtoId()));

        // 3. Coloca a quantidade em uma variavel e seta a quantidade da soma do estoque e a quantidade da request.
        int quantidade = request.quantidade();
        estoqueDeposito.setQuantidade(estoqueDeposito.getQuantidade() + quantidade);

        // 4. Registra as a movimentação da entidade
        Movimentacao entrada = registrar(produto, TipoMovimentacao.ENTRADA, LocalProduto.DEPOSITO, null, quantidade, request.observacao());

        // 5. Atualiza o alerta do depósito (resolve se o saldo voltou acima do mínimo)
        alertaEstoqueService.verificarDeposito(estoqueDeposito);

        return movimentacaoMapper.toMovimentacaoResponse(entrada);
    }

    @Transactional
    public List<MovimentacaoResponse> reposicaoGondola(ReposicaoRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ProdutoNotFoundException(request.produtoId()));

        EstoqueDeposito estoqueDeposito = estoqueDepositoRepository.findByProdutoId(request.produtoId())
                .orElseThrow(() -> new ProdutoEstoqueDepositoNotFoundException(request.produtoId()));

        ItemGondola itemGondola =
                itemGondolaRepository.findByGondolaIdAndProdutoId(request.gondolaId(), produto.getId())
                        .orElseThrow(() -> new ProdutoGondolaErradaException(produto.getId(), request.gondolaId()));

        int quantidade = request.quantidade();

        if (estoqueDeposito.getQuantidade() < quantidade) {
            throw new EstoqueInsuficienteException(produto.getId(), estoqueDeposito.getQuantidade(), quantidade);
        }

        int espacoLivre = itemGondola.getCapacidadeMaxima() - itemGondola.getQuantidadeAtual();
        if (quantidade > espacoLivre) {
            throw new CapacidadeGondolaExcedidaException(itemGondola.getId(), espacoLivre, quantidade);
        }

        // 1. atualiza os saldos (entidades gerenciadas: o Hibernate grava no commit, sem save)
        estoqueDeposito.setQuantidade(estoqueDeposito.getQuantidade() - quantidade);
        itemGondola.setQuantidadeAtual(itemGondola.getQuantidadeAtual() + quantidade);

        // 2. registra as duas pontas da reposicao no historico
        Movimentacao saida = registrar(produto, TipoMovimentacao.REPOSICAO, LocalProduto.DEPOSITO, null, quantidade, request.observacao());
        Movimentacao entrada = registrar(produto, TipoMovimentacao.REPOSICAO, LocalProduto.GONDOLA, itemGondola.getGondola(), quantidade, request.observacao());

        // 3. so agora verifica os alertas, com os saldos ja atualizados
        alertaEstoqueService.verificarDeposito(estoqueDeposito);
        alertaEstoqueService.verificarGondola(itemGondola);

        return List.of(
                movimentacaoMapper.toMovimentacaoResponse(saida),
                movimentacaoMapper.toMovimentacaoResponse(entrada));
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

    private Movimentacao registrar(Produto produto, TipoMovimentacao tipo, LocalProduto local, Gondola gondola, int quantidade, String observacao) {
        return movimentacaoRepository.save(Movimentacao.builder()
                .produto(produto)
                .tipo(tipo)
                .local(local)
                .gondola(gondola)
                .quantidade(quantidade)
                .observacao(observacao)
                .build());
    }
}
