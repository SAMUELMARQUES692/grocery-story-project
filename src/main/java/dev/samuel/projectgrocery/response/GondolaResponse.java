package dev.samuel.projectgrocery.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record GondolaResponse(
        Long id,
        String codigo,
        String corredor,
        String setor,
        String descricao,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt
) {}
