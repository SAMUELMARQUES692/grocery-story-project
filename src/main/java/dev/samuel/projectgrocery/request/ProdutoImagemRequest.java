package dev.samuel.projectgrocery.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record ProdutoImagemRequest(

        @NotNull(message = "O campo 'produtoLojaId' é obrigatório.")
        Long produtoLojaId,

        @NotBlank(message = "O campo 'url' é obrigatório.")
        @URL(message = "O campo 'url' deve ser uma URL válida.")
        String url
) {}
