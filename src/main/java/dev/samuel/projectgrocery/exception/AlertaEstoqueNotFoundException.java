package dev.samuel.projectgrocery.exception;

public class AlertaEstoqueNotFoundException extends RecursoNaoEncontradoException {
    public AlertaEstoqueNotFoundException(Long id) {
        super("ALERTA_ESTOQUE_NAO_ENCONTRADO", "Alerta estoque não encontrado com o ID: " + id);
    }
}
