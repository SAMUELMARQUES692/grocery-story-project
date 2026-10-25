package dev.samuel.projectgrocery.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.samuel.projectgrocery.enums.LocalProduto;
import dev.samuel.projectgrocery.enums.TipoMovimentacao;

import java.time.LocalDateTime;

public record MovimentacaoResponse(
        Long id,
        Long produtoId,
        Long gondolaId,
        TipoMovimentacao tipo,
        LocalProduto local,
        Integer quantidade,
        String observacao,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt
) {}
