package dev.samuel.projectgrocery.exception;

import dev.samuel.projectgrocery.enums.UnidadeMedida;

public class ProdutoFracionavelException extends RegraNegocioException {
    public ProdutoFracionavelException(UnidadeMedida unidadeMedida) {
        super("UNIDADE_DE_MEDIDA_FRACIONAVEL", "O produto com unidade de medida " + unidadeMedida + " é fracionável e não pode ser adicionado ao estoque do depósito");
    }
}
