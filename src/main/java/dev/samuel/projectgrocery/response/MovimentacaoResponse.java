package dev.samuel.projectgrocery.response;

import dev.samuel.projectgrocery.enums.LocalProduto;
import dev.samuel.projectgrocery.enums.TipoMovimentacao;

public record MovimentacaoResponse(
        Long id,
        Long produtoId,
        TipoMovimentacao tipo,
        LocalProduto local,
        Integer quantidade,
        String observacao,
        String createdAt
) {}
