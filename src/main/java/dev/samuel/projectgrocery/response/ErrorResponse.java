package dev.samuel.projectgrocery.response;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        String codigo,
        String mensagem,
        LocalDateTime timestamp,
        Map<String, String> campos
) {
    public ErrorResponse(String codigo, String mensagem) {
        this(codigo, mensagem, LocalDateTime.now(), null);
    }
}
