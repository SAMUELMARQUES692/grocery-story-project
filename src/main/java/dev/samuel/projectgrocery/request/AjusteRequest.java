package dev.samuel.projectgrocery.request;

import dev.samuel.projectgrocery.enums.LocalProduto;
import dev.samuel.projectgrocery.enums.TipoMovimentacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record AjusteRequest(

        @NotNull(message = "O produto é obrigatório")
        Long produtoId,

        @NotNull(message = "O campo de local é obrigatório")
        LocalProduto local,

        Long gondolaId,

        @NotNull(message = "O tipo de movimentação é obrigatório")
        TipoMovimentacao tipo,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantidade,

        @Size(max = 250, message = "A observação deve ter no máximo 250 caracteres")
        @NotBlank(message = "A observação é obrigatória")
        String observacao

) {}
