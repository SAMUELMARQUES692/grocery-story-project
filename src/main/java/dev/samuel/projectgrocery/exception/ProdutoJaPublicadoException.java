package dev.samuel.projectgrocery.exception;

public class ProdutoJaPublicadoException extends RegraNegocioException {
    public ProdutoJaPublicadoException(Long produtoId) {
        super("PRODUTO_JA_PUBLICADO", "Produto com o ID: " + produtoId + " já possui anuncio");
    }
}
