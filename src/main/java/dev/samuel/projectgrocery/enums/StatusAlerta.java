package dev.samuel.projectgrocery.enums;

public enum StatusAlerta {

    ABERTO("Aberto"),
    RESOLVIDO("Resolvido");

    private final String status;

    StatusAlerta(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
