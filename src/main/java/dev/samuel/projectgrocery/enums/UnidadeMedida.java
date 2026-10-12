package dev.samuel.projectgrocery.enums;

import java.util.Arrays;

public enum UnidadeMedida {

    // Contagem
    UN("UN", "Unidade", false),
    DZ("DZ", "Dúzia", false),
    PAR("PAR", "Par", false),

    // Embalagens
    CX("CX", "Caixa", false),
    PCT("PCT", "Pacote", false),
    FD("FD", "Fardo", false),
    BD("BD", "Bandeja", false),
    SC("SC", "Saco", false),
    LT("LT", "Lata", false),
    GF("GF", "Garrafa", false),
    KIT("KIT", "Kit", false),

    // Peso
    KG("KG", "Quilograma", true),
    G("G", "Grama", true),

    // Volume
    L("L", "Litro", true),
    ML("ML", "Mililitro", true),

    // Comprimento
    M("M", "Metro", true);

    private final String sigla;
    private final String descricao;
    private final boolean fracionavel;

    UnidadeMedida(String sigla, String descricao, boolean fracionavel) {
        this.sigla = sigla;
        this.descricao = descricao;
        this.fracionavel = fracionavel;
    }

    public String getSigla() {
        return sigla;
    }

    public String getDescricao() {
        return descricao;
    }

    /** Indica se a quantidade aceita casas decimais (ex.: 1,250 KG). */
    public boolean isFracionavel() {
        return fracionavel;
    }

    public static UnidadeMedida fromSigla(String sigla) {
        return Arrays.stream(values())
                .filter(u -> u.sigla.equalsIgnoreCase(sigla))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unidade de medida inválida: " + sigla));
    }

}
