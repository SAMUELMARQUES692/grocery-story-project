package dev.samuel.projectgrocery.request;

import dev.samuel.projectgrocery.enums.LocalProduto;

import java.time.LocalDateTime;

public record EstoqueBaixoEvent(
        Long alertaId,
        Long produtoId,
        String nomeProduto,
        String codigoBarras,
        LocalProduto local,
        Integer quantidadeAtual,
        Integer minimo,
        LocalDateTime dataHora
) {}
