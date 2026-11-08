package dev.samuel.projectgrocery.response;

public record ProdutoImagemResponse(
        Long id,
        Long produtoLojaId,
        String url,
        Integer ordem,
        String createdAt
) {}
