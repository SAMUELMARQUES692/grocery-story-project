package dev.samuel.projectgrocery.exception;

public class FornecedorNotFoundException extends RecursoNaoEncontradoException  {
    public FornecedorNotFoundException(Long id) {
        super("FORNECEDOR_NAO_ENCONTRADO", "Fornecedor não encontrado com o ID: " + id);
    }
}
