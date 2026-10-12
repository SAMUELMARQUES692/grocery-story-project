package dev.samuel.projectgrocery.exception;

public class CodigoGondolaDuplicadoException extends RegraNegocioException {
    public CodigoGondolaDuplicadoException(String codigo) {
        super("CODIGO_GONDOLA_DUPLICADO", "Codigo de gondola duplicado: " + codigo);
    }
}
