package dev.samuel.projectgrocery.exception;

public class CategoriaDuplicadaException extends RegraNegocioException {
    public CategoriaDuplicadaException(String nome) {
        super("CATEGORIA_DUPLICADA", "Categoria já existe com o nome: " + nome);
    }
}
