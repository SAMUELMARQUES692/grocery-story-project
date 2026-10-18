package dev.samuel.projectgrocery.exception;

public class LimiteSequencialException extends RegraNegocioException {
    public LimiteSequencialException() {
        super("LIMITE_SEQUENCIA_CODIGO_PRODUTO", "Limite de 9999 códigos de produto atingido");
    }
}
