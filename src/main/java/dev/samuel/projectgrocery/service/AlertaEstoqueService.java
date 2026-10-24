package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.entity.AlertaEstoque;
import dev.samuel.projectgrocery.entity.EstoqueDeposito;
import dev.samuel.projectgrocery.entity.ItemGondola;
import dev.samuel.projectgrocery.entity.Produto;
import dev.samuel.projectgrocery.enums.LocalProduto;
import dev.samuel.projectgrocery.enums.StatusAlerta;
import dev.samuel.projectgrocery.exception.AlertaEstoqueNotFoundException;
import dev.samuel.projectgrocery.mapper.AlertaEstoqueMapper;
import dev.samuel.projectgrocery.repository.AlertaEstoqueRepository;
import dev.samuel.projectgrocery.request.EstoqueBaixoEvent;
import dev.samuel.projectgrocery.response.AlertaEstoqueResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class AlertaEstoqueService {

    private final AlertaEstoqueRepository alertaEstoqueRepository;
    private final AlertaEstoqueMapper alertaEstoqueMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void verificarDeposito(EstoqueDeposito estoque) {
        verificar(estoque.getProduto(), LocalProduto.DEPOSITO, estoque.getQuantidade(), estoque.getEstoqueMinimo());
    }

    @Transactional
    public void verificarGondola(ItemGondola itemGondola) {
        verificar(itemGondola.getProduto(), LocalProduto.GONDOLA, itemGondola.getQuantidadeAtual(), itemGondola.getCapacidadeMinima());
    }

    private void verificar(Produto produto, LocalProduto local, int quantidadeAtual, int minimo) {
        if (quantidadeAtual <= minimo) {
            abrirAlertaSeNecessario(produto, local, quantidadeAtual, minimo);
        } else {
            resolverAlertaAberto(produto, local);
        }
    }

    private void abrirAlertaSeNecessario(Produto produto, LocalProduto local, int quantidadeAtual, int minimo) {
        // Anti-spam: se ja existe alerta ABERTO para o produto nesse local, nao abre outro nem manda outro e-mail
        if (alertaEstoqueRepository.existsByProdutoIdAndLocalAndStatus(produto.getId(), local, StatusAlerta.ABERTO)) {
            return;
        }

        AlertaEstoque alertaEstoque = alertaEstoqueRepository.save(
                AlertaEstoque.builder()
                        .produto(produto)
                        .local(local)
                        .quantidadeNoMomento(quantidadeAtual)
                        .build()// status ABERTO e emailEnviado false vem dos defaults da entidade
        );

        log.info("Alerta {} aberto: produto {} com {} unidade(s) em {}", alertaEstoque.getId(), produto.getId(), quantidadeAtual, local);

        // Montado aqui, dentro da transacao, para nao acessar o produto (LAZY) depois do commit.
        // Quem envia para o RabbitMQ e o EstoqueBaixoPublisher, com @TransactionalEventListener(AFTER_COMMIT).
        applicationEventPublisher.publishEvent(new EstoqueBaixoEvent(
                alertaEstoque.getId(),
                produto.getId(),
                produto.getNome(),
                produto.getCodigoBarras(),
                local,
                quantidadeAtual,
                minimo,
                LocalDateTime.now()
        ));
    }

    private void resolverAlertaAberto(Produto produto, LocalProduto local) {
        alertaEstoqueRepository.findByProdutoIdAndLocalAndStatus(produto.getId(), local, StatusAlerta.ABERTO).ifPresent(alertaEstoque -> {
            alertaEstoque.setStatus(StatusAlerta.RESOLVIDO);
            log.info("Alerta {} resolvido: produto {} voltou acima da quantidade minima em {}", alertaEstoque.getId(), produto.getId(), local);
        });
        // Sem save(): a entidade esta gerenciada e o Hibernate grava a alteracao no commit.
    }

    @Transactional
    public void marcarEmailEnviado(Long alertaId) {
        AlertaEstoque alerta = alertaEstoqueRepository.findById(alertaId)
                .orElseThrow(() -> new AlertaEstoqueNotFoundException(alertaId));
        alerta.setEmailEnviado(true);
    }

    @Transactional(readOnly = true)
    public List<AlertaEstoqueResponse> listar(StatusAlerta statusAlerta) {
        List<AlertaEstoque> alertaEstoques = (statusAlerta == null)
                ? alertaEstoqueRepository.findAllByOrderByCreatedAtDesc()
                : alertaEstoqueRepository.findByStatusOrderByCreatedAtDesc(statusAlerta);
        return alertaEstoqueMapper.toAlertaEstoqueResponseList(alertaEstoques);
    }

    @Transactional(readOnly = true)
    public AlertaEstoqueResponse buscarPorId(Long id) {
        return alertaEstoqueRepository.findWithProdutoById(id)
                .map(alertaEstoqueMapper::toAlertaEstoqueResponse)
                .orElseThrow(() -> new AlertaEstoqueNotFoundException(id));
    }

}
