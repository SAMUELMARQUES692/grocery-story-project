package dev.samuel.projectgrocery.exception;

import dev.samuel.projectgrocery.enums.TipoMovimentacao;

public class TipoIncorretoException extends RegraNegocioException {
    public TipoIncorretoException(TipoMovimentacao tipo) {
        super("TIPO_INCORRETO", "O tipo " + tipo + " não pode ser usado nesta requisição");
    }
}
