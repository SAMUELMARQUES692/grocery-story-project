package dev.samuel.projectgrocery.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record ItemGondolaResponse(
        Long id,
        Long gondolaId,
        Long produtoId,
        Integer prateleira,
        Integer quantidadeAtual,
        Integer capacidadeMaxima,
        Integer capacidadeMinima,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt
) {}
