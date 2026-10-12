package dev.samuel.projectgrocery.exception;

public class CnpjDuplicadoException extends RegraNegocioException {
    public CnpjDuplicadoException(String cnpj) {
        super("CNPJ_JA_CADASTRADO", "O CNPJ " + cnpj + " já está cadastrado.");
    }
}
