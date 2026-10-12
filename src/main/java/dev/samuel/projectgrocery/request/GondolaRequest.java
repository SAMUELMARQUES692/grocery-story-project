package dev.samuel.projectgrocery.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GondolaRequest(

        @NotBlank(message = "O código da gôndola é obrigatório")
        @Size(max = 100, message = "O código da gôndola deve ter no máximo 100 caracteres")
        String codigo,

        @NotBlank(message = "O corredor da gôndola é obrigatório")
        @Size(max = 100, message = "O corredor da gôndola deve ter no máximo 100 caracteres")
        String corredor,

        @NotBlank(message = "O setor da gôndola é obrigatório")
        @Size(max = 100, message = "O setor da gôndola deve ter no máximo 100 caracteres")
        String setor,

        @Size(max = 500, message = "A descrição da gôndola deve ter no máximo 500 caracteres")
        String descricao
) {}
