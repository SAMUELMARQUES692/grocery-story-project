package dev.samuel.projectgrocery.request;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoLojaRequest(

        @Size(max = 1000, message = "O campo 'descricao' deve ter no máximo 1000 caracteres.")
        String descricao,

        @Positive(message = "O campo 'precoPromocional' deve ser um valor positivo.")
        BigDecimal precoPromocional,

        Boolean destaque
) {}
