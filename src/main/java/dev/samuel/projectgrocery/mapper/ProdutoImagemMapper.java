package dev.samuel.projectgrocery.mapper;

import dev.samuel.projectgrocery.entity.ProdutoImagem;
import dev.samuel.projectgrocery.request.ProdutoImagemRequest;
import dev.samuel.projectgrocery.response.ProdutoImagemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProdutoImagemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produtoLoja", ignore = true)
    @Mapping(target = "ordem", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ProdutoImagem toProdutoImagem(ProdutoImagemRequest request);

    @Mapping(target = "produtoLojaId", source = "produtoLoja.id")
    ProdutoImagemResponse toProdutoImagemResponse(ProdutoImagem produtoImagem);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "produtoLoja", ignore = true)
    @Mapping(target = "ordem", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void atualizarProdutoImagem(ProdutoImagemRequest request, @MappingTarget ProdutoImagem produtoImagem);

}
