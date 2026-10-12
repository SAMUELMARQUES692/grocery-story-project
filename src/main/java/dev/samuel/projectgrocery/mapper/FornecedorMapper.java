package dev.samuel.projectgrocery.mapper;

import dev.samuel.projectgrocery.entity.Fornecedor;
import dev.samuel.projectgrocery.request.FornecedorRequest;
import dev.samuel.projectgrocery.response.FornecedorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface FornecedorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Fornecedor toFornecedor(FornecedorRequest request);

    FornecedorResponse toFornecedorResponse(Fornecedor fornecedor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarFornecedor(FornecedorRequest request, @MappingTarget Fornecedor fornecedor);

}
