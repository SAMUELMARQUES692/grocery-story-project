package dev.samuel.projectgrocery.exception;

public class ProdutoInativoException extends RegraNegocioException {
    public ProdutoInativoException(Long id) {
        super("PRODUTO_INATIVO", "O produto com ID " + id + " está inativo e não pode ser adicionado ao estoque do depósito");
    }
}
