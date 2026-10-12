package dev.samuel.projectgrocery.request;

import dev.samuel.projectgrocery.enums.UnidadeMedida;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProdutoRequest(

    @NotBlank(message = "Campo de codigo de barras é obrigatório")
    @Pattern(regexp = "\\d{8}|\\d{12,14}", message = "Código de barras deve ter 8, 12, 13 ou 14 dígitos")
    String codigoBarras,

    @NotBlank(message = "Campo de nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    String nome,

    @Positive(message = "Campo de preço de custo deve ser maior que zero")
    @NotNull(message = "Campo de preço de custo é obrigatório")
    BigDecimal precoCusto,

    @Positive(message = "Campo de preço de venda deve ser maior que zero")
    @NotNull(message = "Campo de preço de venda é obrigatório")
    BigDecimal precoVenda,

    @NotNull(message = "Campo de unidade de medida é obrigatório")
    UnidadeMedida unidadeMedida,

    @NotNull(message = "Campo de categoria é obrigatório")
    Long categoriaId,

    @NotNull(message = "Campo de fornecedor é obrigatório")
    Long fornecedorId
) {}
