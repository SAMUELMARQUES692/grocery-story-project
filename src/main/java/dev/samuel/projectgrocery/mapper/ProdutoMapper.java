package dev.samuel.projectgrocery.mapper;

import dev.samuel.projectgrocery.entity.Produto;
import dev.samuel.projectgrocery.request.ProdutoRequest;
import dev.samuel.projectgrocery.response.ProdutoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProdutoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "fornecedor", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Produto toProduto(ProdutoRequest request);

    @Mapping(target = "categoriaId", source = "categoria.id")
    @Mapping(target = "fornecedorId", source = "fornecedor.id")
    ProdutoResponse toProdutoResponse(Produto produto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "fornecedor", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void atualizarProduto(ProdutoRequest request, @MappingTarget Produto produto);

}
