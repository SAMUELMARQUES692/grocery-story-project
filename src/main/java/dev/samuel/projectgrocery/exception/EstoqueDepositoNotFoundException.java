package dev.samuel.projectgrocery.exception;

public class EstoqueDepositoNotFoundException extends RecursoNaoEncontradoException {
    public EstoqueDepositoNotFoundException(Long id) {
        super("ESTOQUE_DEPOSITO_NAO_ENCONTRADO", "Estoque deposito não encontrado com o ID: " + id);
    }
}
