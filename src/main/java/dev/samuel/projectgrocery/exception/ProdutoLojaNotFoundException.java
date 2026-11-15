package dev.samuel.projectgrocery.exception;

public class ProdutoLojaNotFoundException extends RecursoNaoEncontradoException {
    public ProdutoLojaNotFoundException(Long id) {
        super("PRODUTO_LOJA_NAO_ENCONTRADO", "Produto Loja não encontrado com o ID: " + id);
    }
}
