package dev.samuel.projectgrocery.exception;

public class CapacidadeGondolaExcedidaException extends RegraNegocioException {
    public CapacidadeGondolaExcedidaException(Long gondolaId, int espaçoLivre, int quantidade) {
        super("CAPACIDADE_GONDOLA_EXCEDIDA", "A capacidade da gôndola com o ID: " + gondolaId + " tem " + espaçoLivre + " vagas, não suporta a quantidade de " + quantidade);
    }
}
