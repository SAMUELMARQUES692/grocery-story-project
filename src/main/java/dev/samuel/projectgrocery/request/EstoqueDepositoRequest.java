package dev.samuel.projectgrocery.request;

import jakarta.validation.constraints.*;

public record EstoqueDepositoRequest(

        @NotNull(message = "O produto é obrigatório")
        Long produtoId,

        @NotNull(message = "A quantidade é obrigatória")
        @PositiveOrZero(message = "A quantidade não pode ser negativa")
        Integer quantidade,

        @NotNull(message = "O estoque mínimo é obrigatório")
        @PositiveOrZero(message = "O estoque mínimo não pode ser negativo")
        Integer estoqueMinimo,

        @NotBlank(message = "A localização é obrigatória")
        @Size(max = 100, message = "A localização deve ter no máximo 100 caracteres")
        String localizacao
) {}
