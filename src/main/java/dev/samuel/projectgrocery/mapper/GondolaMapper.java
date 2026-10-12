package dev.samuel.projectgrocery.mapper;

import dev.samuel.projectgrocery.entity.Gondola;
import dev.samuel.projectgrocery.request.GondolaRequest;
import dev.samuel.projectgrocery.response.GondolaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GondolaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Gondola toGondola(GondolaRequest request);

    GondolaResponse toGondolaResponse(Gondola gondola);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarGondola(GondolaRequest request, @MappingTarget Gondola gondola);
}
