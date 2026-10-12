package dev.samuel.projectgrocery.exception;

public class EstoqueDepositoDuplicadoException extends RegraNegocioException {
    public EstoqueDepositoDuplicadoException(Long produtoId) {
        super("ESTOQUE_DEPOSITO_DUPLICADO", "O produto com o ID " + produtoId + " já esta cadastrado no estoque do depósito");
    }
}
