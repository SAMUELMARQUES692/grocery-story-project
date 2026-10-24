package dev.samuel.projectgrocery.mapper;

import dev.samuel.projectgrocery.entity.AlertaEstoque;
import dev.samuel.projectgrocery.response.AlertaEstoqueResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface AlertaEstoqueMapper {

    @Mapping(target = "produtoId", source = "produto.id")
    @Mapping(target = "nomeProduto", source = "produto.nome")
    @Mapping(target = "codigoBarras", source = "produto.codigoBarras")
    AlertaEstoqueResponse toAlertaEstoqueResponse(AlertaEstoque alerta);

    List<AlertaEstoqueResponse> toAlertaEstoqueResponseList(List<AlertaEstoque> alertas);

}
