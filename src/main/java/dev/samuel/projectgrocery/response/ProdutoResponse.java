package dev.samuel.projectgrocery.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.samuel.projectgrocery.enums.UnidadeMedida;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProdutoResponse(
    Long id,
    String codigoBarras,
    String nome,
    BigDecimal precoCusto,
    BigDecimal precoVenda,
    UnidadeMedida unidadeMedida,
    boolean ativo,
    Long categoriaId,
    Long fornecedorId,

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdAt
) {}
