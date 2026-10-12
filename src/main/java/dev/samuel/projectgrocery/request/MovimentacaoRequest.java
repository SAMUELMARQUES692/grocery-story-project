package dev.samuel.projectgrocery.request;

import dev.samuel.projectgrocery.enums.LocalProduto;
import dev.samuel.projectgrocery.enums.TipoMovimentacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record MovimentacaoRequest(

        @NotNull(message = "O campo de produto é obrigatório")
        Long produtoId,

        @NotNull(message = "O tipo de movimentação é obrigatório")
        TipoMovimentacao tipo,

        @NotNull(message = "O local do produto é obrigatório")
        LocalProduto local,

        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser maior que zero")
        Integer quantidade,

        @Size(max = 250, message = "A observação deve ter no máximo 250 caracteres")
        String observacao

) {}
