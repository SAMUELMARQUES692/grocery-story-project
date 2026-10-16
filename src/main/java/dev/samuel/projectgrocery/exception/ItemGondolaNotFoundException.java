package dev.samuel.projectgrocery.exception;

public class ItemGondolaNotFoundException extends RecursoNaoEncontradoException {
    public ItemGondolaNotFoundException(Long id) {
        super ("ITEM_GONDOLA_NAO_ENCONTRADO", "Item gondola não encontrado com o ID: " + id);
    }
}
