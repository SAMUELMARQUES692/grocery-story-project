package dev.samuel.projectgrocery.request;

import jakarta.validation.constraints.NotNull;

public record AtualizarVisibilidadeRequest(

        @NotNull(message = "O campo de visibilidade é obrigatório")
        Boolean visibilidade
) {}
