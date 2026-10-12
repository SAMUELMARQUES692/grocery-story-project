package dev.samuel.projectgrocery.exception;

public class ProdutoEstoqueDepositoNotFoundException extends RecursoNaoEncontradoException {
    public ProdutoEstoqueDepositoNotFoundException(Long id) {
        super("PRODUTO_ESTOQUE_DEPOSITO_NAO_ENCONTRADO", "Produto com o ID: " + id + " não foi encontrado no estoque");
    }
}
