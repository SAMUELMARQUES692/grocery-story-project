package dev.samuel.projectgrocery.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record CategoriaResponse(
    Long id,
    String nome,
    String descricao,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt
) {}
