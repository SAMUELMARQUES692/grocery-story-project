package dev.samuel.projectgrocery.mapper;

import dev.samuel.projectgrocery.entity.ProdutoLoja;
import dev.samuel.projectgrocery.request.ProdutoLojaRequest;
import dev.samuel.projectgrocery.response.ProdutoLojaResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProdutoLojaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "visivel", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProdutoLoja toProdutoLoja(ProdutoLojaRequest request);

    @Mapping(target = "produtoId", source = "produto.id")
    ProdutoLojaResponse toProdutoLojaResponse(ProdutoLoja produtoLoja);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produto", ignore = true)
    @Mapping(target = "visivel", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarProdutoLoja(ProdutoLojaRequest request, @MappingTarget ProdutoLoja produtoLoja);

}
