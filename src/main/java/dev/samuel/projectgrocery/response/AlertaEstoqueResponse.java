package dev.samuel.projectgrocery.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.samuel.projectgrocery.enums.LocalProduto;
import dev.samuel.projectgrocery.enums.StatusAlerta;

import java.time.LocalDateTime;

public record AlertaEstoqueResponse(
        Long id,
        Long produtoId,
        String nomeProduto,
        String codigoBarras,
        LocalProduto local,
        Integer quantidadeNoMomento,
        StatusAlerta status,
        boolean emailEnviado,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt




) {}
