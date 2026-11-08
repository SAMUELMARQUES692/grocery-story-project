package dev.samuel.projectgrocery.response;

public record ProdutoLojaResponse(
        Long id,
        Long produtoId,
        String descricao,
        String precoPromocional,
        boolean visivel,
        boolean destaque,
        String createdAt,
        String updatedAt
) {}
