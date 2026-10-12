package dev.samuel.projectgrocery.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaRequest (

        @NotBlank(message = "O nome da categoria é obrigatório")
        @Size(max = 250, message = "O nome deve ter no máximo 250 caracteres")
        String nome,

        @NotBlank(message = "A descrição da categoria é obrigatório")
        @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
        String descricao
) {}
