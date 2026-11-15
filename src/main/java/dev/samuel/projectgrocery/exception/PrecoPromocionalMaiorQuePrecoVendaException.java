package dev.samuel.projectgrocery.exception;

import java.math.BigDecimal;

public class PrecoPromocionalMaiorQuePrecoVendaException extends RegraNegocioException {
    public PrecoPromocionalMaiorQuePrecoVendaException(BigDecimal precoPromocional, BigDecimal precoVenda) {
        super("PRECO_PROMOCIONAL_MAIOR_QUE_PRECO_VENDA", "O preço promocional deve ser menor que o preço de venda,\n Preço Venda: " + precoVenda + "\n Preço Promocional: " + precoPromocional);
    }
}
