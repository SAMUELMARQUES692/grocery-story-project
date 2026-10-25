package dev.samuel.projectgrocery.exception;

public class GondolaIdInvalidoException extends RegraNegocioException {
    public GondolaIdInvalidoException(String message) {
        super("GONDOLA_ID_INVALIDO", message);
    }
}
