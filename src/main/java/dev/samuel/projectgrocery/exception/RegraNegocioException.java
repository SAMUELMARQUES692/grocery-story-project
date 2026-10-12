package dev.samuel.projectgrocery.exception;

public abstract class RegraNegocioException extends RuntimeException {
    private final String codigo;

    protected RegraNegocioException(String codigo, String mensagem) {
        super(mensagem);
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

}
