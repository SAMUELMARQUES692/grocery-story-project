package dev.samuel.projectgrocery.mapper;

import dev.samuel.projectgrocery.entity.ItemGondola;
import dev.samuel.projectgrocery.request.ItemGondolaRequest;
import dev.samuel.projectgrocery.response.ItemGondolaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ItemGondolaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gondola", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ItemGondola toItemGondola(ItemGondolaRequest request);

    @Mapping(target = "gondolaId", source = "gondola.id")
    @Mapping(target = "produtoId", source = "produto.id")
    ItemGondolaResponse toItemGondolaResponse(ItemGondola itemGondola);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "gondola", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "quantidadeAtual", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarItemGondola(ItemGondolaRequest request, @MappingTarget ItemGondola itemGondola);

}
