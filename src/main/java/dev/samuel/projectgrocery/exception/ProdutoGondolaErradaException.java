package dev.samuel.projectgrocery.exception;

public class ProdutoGondolaErradaException extends RegraNegocioException {
    public ProdutoGondolaErradaException(Long produtoId, Long gondolaId) {
        super("PRODUTO_ITEM_GONDOLA_DIVERGENTE", "Produto de ID: " + produtoId + " não foi encontrado na gondola " + gondolaId);
    }
}
