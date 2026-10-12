package dev.samuel.projectgrocery.exception;

public class GondolaNotFoundException extends RecursoNaoEncontradoException {
    public GondolaNotFoundException(Long id) {
        super("GONDOLA_NAO_ENCONTRADA", "Gondola não encontrada com o ID: " + id);
    }
}
