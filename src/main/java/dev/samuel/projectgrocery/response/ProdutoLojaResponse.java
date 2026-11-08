package dev.samuel.projectgrocery.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;

public record ProdutoLojaResponse(
        Long id,
        Long produtoId,
        String descricao,
        BigDecimal precoPromocional,
        boolean visivel,
        boolean destaque,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        String createdAt,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        String updatedAt
) {}
