package dev.samuel.projectgrocery.response;

import com.fasterxml.jackson.annotation.JsonFormat;

public record ProdutoImagemResponse(
        Long id,
        Long produtoLojaId,
        String url,
        Integer ordem,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        String createdAt
) {}
