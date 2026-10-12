package dev.samuel.projectgrocery.mapper;

import dev.samuel.projectgrocery.entity.EstoqueDeposito;
import dev.samuel.projectgrocery.request.EstoqueDepositoRequest;
import dev.samuel.projectgrocery.response.EstoqueDepositoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EstoqueDepositoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    EstoqueDeposito toEstoqueDeposito(EstoqueDepositoRequest request);

    @Mapping(target = "produtoId", source = "produto.id")
    EstoqueDepositoResponse toEstoqueDepositoResponse(EstoqueDeposito estoqueDeposito);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "quantidade", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarEstoqueDeposito(EstoqueDepositoRequest request, @MappingTarget EstoqueDeposito estoqueDeposito);

}
