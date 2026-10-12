package dev.samuel.projectgrocery.exception;

public class ProdutoNotFoundException extends RecursoNaoEncontradoException {
    public ProdutoNotFoundException(Long id) {
        super("PRODUTO_NAO_ENCONTRADO", "Produto não encontrado com o ID: " + id);
    }
}
