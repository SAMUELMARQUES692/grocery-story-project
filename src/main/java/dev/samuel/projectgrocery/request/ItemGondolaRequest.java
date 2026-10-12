package dev.samuel.projectgrocery.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemGondolaRequest(

       @NotNull(message = "Id da gondola não pode ser nulo")
       Long gondolaId,

       @NotNull(message = "Id do produto não pode ser nulo")
       Long produtoId,

       @Positive(message = "Prateleira deve ser um número positivo")
       @NotNull(message = "Prateleira não pode ser nula")
       Integer prateleira,

       @Positive(message = "Quantidade atual deve ser um número positivo")
       @NotNull(message = "Quantidade atual não pode ser nula")
       Integer quantidadeAtual,

       @Positive(message = "Capacidade maxima deve ser um número positivo")
       @NotNull(message = "Capacidade maxima não pode ser nula")
       Integer capacidadeMaxima,

       @Positive(message = "Capacidade minima deve ser um número positivo")
       @NotNull(message = "Capacidade minima não pode ser nula")
       Integer capacidadeMinima
) {}
