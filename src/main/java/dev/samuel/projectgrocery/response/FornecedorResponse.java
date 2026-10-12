package dev.samuel.projectgrocery.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record FornecedorResponse(
    Long id,
    String razaoSocial,
    String cnpj,
    String email,
    String telefone,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt
) {}
