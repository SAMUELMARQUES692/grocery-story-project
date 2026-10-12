package dev.samuel.projectgrocery.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record EstoqueDepositoResponse(
            Long id,
            Long produtoId,
            Integer quantidade,
            Integer estoqueMinimo,
            String localizacao,

            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime createdAt
) {}
