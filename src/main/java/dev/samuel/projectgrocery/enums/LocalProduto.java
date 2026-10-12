package dev.samuel.projectgrocery.enums;

import lombok.Getter;

@Getter
public enum LocalProduto {

    DEPOSITO("Depósito"),
    GONDOLA("Gôndola");

    private final String local;

    LocalProduto(String local) {
        this.local = local;
    }
}



