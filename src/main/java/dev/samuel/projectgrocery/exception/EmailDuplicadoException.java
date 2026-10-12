package dev.samuel.projectgrocery.exception;

public class EmailDuplicadoException extends RegraNegocioException {
    public EmailDuplicadoException(String email) {
        super("EMAIL_JA_CADASTRADO", "O email " + email + " já está cadastrado.");
    }
}
