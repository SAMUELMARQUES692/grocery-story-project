package dev.samuel.projectgrocery.exception;

public class CategoriaNotFoundException extends RecursoNaoEncontradoException {
    public CategoriaNotFoundException(Long id) {
        super("CATEGORIA_NAO_ENCONTRADA", "Categoria não encontrada com o ID: " + id);
    }
}
