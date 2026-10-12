package dev.samuel.projectgrocery.exception;

public class ProdutoItemGondolaNotFoundException extends RecursoNaoEncontradoException {
    public ProdutoItemGondolaNotFoundException(Long id) {
        super("PRODUTO_ITEM_GONDOLA_NAO_ENCONTRADO", "Produto de ID: " + id + " não foi encontrado nas gondolas");
    }
}
