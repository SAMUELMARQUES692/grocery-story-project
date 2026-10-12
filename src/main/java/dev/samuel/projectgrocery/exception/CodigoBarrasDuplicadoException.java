package dev.samuel.projectgrocery.exception;

public class CodigoBarrasDuplicadoException extends RegraNegocioException  {
    public CodigoBarrasDuplicadoException(String codigoBarras) {
        super("CODIGO_DE_BARRAS_JA_EXISTENTE", "Produto com o código de barras " + codigoBarras + " já existe.");
    }
}
