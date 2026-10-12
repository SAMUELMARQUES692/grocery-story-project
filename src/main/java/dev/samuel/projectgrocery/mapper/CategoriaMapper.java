package dev.samuel.projectgrocery.mapper;

import dev.samuel.projectgrocery.entity.Categoria;
import dev.samuel.projectgrocery.request.CategoriaRequest;
import dev.samuel.projectgrocery.response.CategoriaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Categoria toCategoria(CategoriaRequest request);

    CategoriaResponse toCategoriaResponse(Categoria categoria);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarCategoria(CategoriaRequest request, @MappingTarget Categoria categoria);

}
