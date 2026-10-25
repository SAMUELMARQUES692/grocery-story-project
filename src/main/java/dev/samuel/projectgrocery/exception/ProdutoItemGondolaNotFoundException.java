package dev.samuel.projectgrocery.exception;

public class ProdutoItemGondolaNotFoundException extends RecursoNaoEncontradoException {
    public ProdutoItemGondolaNotFoundException(Long produtoId) {
        super("PRODUTO_ITEM_GONDOLA_NAO_ENCONTRADO", "Produto de ID: " + produtoId);
    }
}
