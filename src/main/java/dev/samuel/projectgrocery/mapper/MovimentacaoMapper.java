package dev.samuel.projectgrocery.mapper;

import dev.samuel.projectgrocery.entity.Movimentacao;
import dev.samuel.projectgrocery.request.MovimentacaoRequest;
import dev.samuel.projectgrocery.response.MovimentacaoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MovimentacaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Movimentacao toMovimentacao(MovimentacaoRequest request);

    @Mapping(target = "produtoId", source = "produto.id")
    @Mapping(target = "gondolaId", source = "gondola.id")
    MovimentacaoResponse toMovimentacaoResponse(Movimentacao movimentacao);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void atualizarMovimentacao(MovimentacaoRequest request, @MappingTarget Movimentacao movimentacao);

}
