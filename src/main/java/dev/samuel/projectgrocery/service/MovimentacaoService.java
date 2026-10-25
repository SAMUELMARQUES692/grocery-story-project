package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.entity.*;
import dev.samuel.projectgrocery.enums.LocalProduto;
import dev.samuel.projectgrocery.enums.TipoMovimentacao;
import dev.samuel.projectgrocery.exception.*;
import dev.samuel.projectgrocery.mapper.MovimentacaoMapper;
import dev.samuel.projectgrocery.repository.*;
import dev.samuel.projectgrocery.request.*;
import dev.samuel.projectgrocery.response.MovimentacaoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.IllegalFormatCodePointException;
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
    private final TipoMovimentacao tipo;

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

        ItemGondola itemGondola = itemGondolaRepository.findByGondolaIdAndProdutoId(request.gondolaId(), produto.getId())
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
    public MovimentacaoResponse venda(VendaRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ProdutoNotFoundException(request.produtoId()));

        // 1. Verifica se o produto esta ativo
        if (!produto.isAtivo()) {
            throw new ProdutoInativoException(produto.getId());
        }

        // 2. Verifica se o produto esta na gondola certa
        ItemGondola itemGondola = itemGondolaRepository.findByGondolaIdAndProdutoId(request.gondolaId(), produto.getId())
                .orElseThrow(() -> new ProdutoGondolaErradaException(produto.getId(), request.gondolaId()));

        int quantidade = request.quantidade();

        // 3. Verifica se o item na gondola tem saldo suficiente para fazer a venda
        if (itemGondola.getQuantidadeAtual() < quantidade) {
            throw new EstoqueInsuficienteException(produto.getId(), itemGondola.getQuantidadeAtual(), quantidade);
        }

        // 4. atualiza os saldos (entidades gerenciadas: o Hibernate grava no commit, sem save)
        itemGondola.setQuantidadeAtual(itemGondola.getQuantidadeAtual() - quantidade);

        // 5. Registra as a movimentação da entidade
        Movimentacao saida = registrar(produto, TipoMovimentacao.VENDA, LocalProduto.GONDOLA, itemGondola.getGondola(), quantidade, request.observacao());

        // 6. Atualiza o alerta da gondola (verifica se possição está com quantidade minima)
        alertaEstoqueService.verificarGondola(itemGondola);

        return movimentacaoMapper.toMovimentacaoResponse(saida);
    }

    @Transactional
    public MovimentacaoResponse ajusteEstoque(AjusteRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ProdutoNotFoundException(request.produtoId()));

        if (request.tipo() != TipoMovimentacao.AJUSTE_ENTRADA && request.tipo() != TipoMovimentacao.AJUSTE_SAIDA) {
            throw new TipoIncorretoException(request.tipo());
        }
        validarGondolaId(request.local(), request.gondolaId());

        Movimentacao movimentacao = movimentarSaldo(produto, request.tipo(), request.local(),
                request.gondolaId(), request.quantidade(), request.observacao());
        return movimentacaoMapper.toMovimentacaoResponse(movimentacao);
    }

    @Transactional
    public MovimentacaoResponse perdaEstoque(PerdaRequest request) {
        Produto produto = produtoRepository.findById(request.produtoId())
                .orElseThrow(() -> new ProdutoNotFoundException(request.produtoId()));

        validarGondolaId(request.local(), request.gondolaId());

        Movimentacao movimentacao = movimentarSaldo(produto, TipoMovimentacao.PERDA, request.local(),
                request.gondolaId(), request.quantidade(), request.observacao());
        return movimentacaoMapper.toMovimentacaoResponse(movimentacao);
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

    private void validarGondolaId(LocalProduto local, Long gondolaId) {
        if (local == LocalProduto.GONDOLA && gondolaId == null) {
            throw new GondolaIdInvalidoException("A gôndola é obrigatória para movimentações na gôndola");
        }
        if (local == LocalProduto.DEPOSITO && gondolaId != null) {
            throw new GondolaIdInvalidoException("Movimentações no depósito não devem informar gôndola");
        }
    }

    private Movimentacao movimentarSaldo(Produto produto, TipoMovimentacao tipo, LocalProduto local, Long gondolaId, int quantidade, String observacao) {
        int delta = tipo.somaNoSaldo(local) ? quantidade : -quantidade;

        if (local == LocalProduto.DEPOSITO) {
            EstoqueDeposito estoqueDeposito = estoqueDepositoRepository.findByProdutoId(produto.getId())
                    .orElseThrow(() -> new ProdutoEstoqueDepositoNotFoundException(produto.getId()));

            int novoSaldo = estoqueDeposito.getQuantidade() + delta;
            if (novoSaldo < 0) {
                throw new EstoqueInsuficienteException(produto.getId(), estoqueDeposito.getQuantidade(), quantidade);
            }

            estoqueDeposito.setQuantidade(novoSaldo);
            Movimentacao movimentacao = registrar(produto, tipo, LocalProduto.DEPOSITO, null, quantidade, observacao);
            alertaEstoqueService.verificarDeposito(estoqueDeposito);
            return movimentacao;
        }

        ItemGondola itemGondola = itemGondolaRepository.findByGondolaIdAndProdutoId(gondolaId, produto.getId())
                .orElseThrow(() -> new ProdutoGondolaErradaException(produto.getId(), gondolaId));

        int novoSaldo = itemGondola.getQuantidadeAtual() + delta;
        if (novoSaldo < 0) {
            throw new EstoqueInsuficienteException(produto.getId(), itemGondola.getQuantidadeAtual(), quantidade);
        }
        if (novoSaldo > itemGondola.getCapacidadeMaxima()) {
            int espacoLivre = itemGondola.getCapacidadeMaxima() - itemGondola.getQuantidadeAtual();
            throw new CapacidadeGondolaExcedidaException(itemGondola.getId(), espacoLivre, quantidade);
        }

        itemGondola.setQuantidadeAtual(novoSaldo);
        Movimentacao movimentacao = registrar(produto, tipo, LocalProduto.GONDOLA, itemGondola.getGondola(),
                quantidade, observacao);
        alertaEstoqueService.verificarGondola(itemGondola);
        return movimentacao;
    }

}
