package dev.samuel.projectgrocery.enums;

import lombok.Getter;

@Getter
public enum TipoMovimentacao {

    ENTRADA("Entrada de mercadoria", true),
    REPOSICAO("Reposição (depósito -> gôndola)", null),
    VENDA("Venda", false),
    AJUSTE_ENTRADA("Ajuste de inventário (+)", true),
    AJUSTE_SAIDA("Ajuste de inventário (-)", false),
    PERDA("Perda / avaria / vencimento", false);

    private final String descricao;
    /** true = soma no saldo, false = subtrai, null = depende do local */
    private final Boolean entrada;

    TipoMovimentacao(String descricao, Boolean entrada) {
        this.descricao = descricao;
        this.entrada = entrada;
    }

    /** Define se a movimentação soma ou subtrai do saldo do local informado. */
    public boolean somaNoSaldo(LocalProduto local) {
        if (this == REPOSICAO) {
            return local == LocalProduto.GONDOLA; // sai do depósito, entra na gôndola
        }
        return entrada;
    }
}