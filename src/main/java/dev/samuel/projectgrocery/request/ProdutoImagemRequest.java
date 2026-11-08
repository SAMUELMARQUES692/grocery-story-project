package dev.samuel.projectgrocery.request;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record ProdutoImagemRequest(

        @NotBlank(message = "O campo 'url' é obrigatório.")
        @URL(message = "O campo 'url' deve ser uma URL válida.")
        String url
) {}
