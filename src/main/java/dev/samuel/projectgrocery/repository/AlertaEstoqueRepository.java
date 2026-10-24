package dev.samuel.projectgrocery.repository;

import dev.samuel.projectgrocery.entity.AlertaEstoque;
import dev.samuel.projectgrocery.enums.LocalProduto;
import dev.samuel.projectgrocery.enums.StatusAlerta;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlertaEstoqueRepository extends JpaRepository<AlertaEstoque, Long> {

    boolean existsByProdutoIdAndLocalAndStatus(Long produtoId, LocalProduto local, StatusAlerta status);

    Optional<AlertaEstoque> findByProdutoIdAndLocalAndStatus(Long produtoId, LocalProduto local, StatusAlerta status);

    // ---------------------------------------------------- usados nas consultas da API
    // @EntityGraph carrega o produto no mesmo SELECT (JOIN), evitando uma consulta extra
    // por alerta (problema N+1) quando o mapper le produto.nome e produto.codigoBarras.

    @EntityGraph(attributePaths = "produto")
    List<AlertaEstoque> findByStatusOrderByCreatedAtDesc(StatusAlerta status);

    @EntityGraph(attributePaths = "produto")
    List<AlertaEstoque> findAllByOrderByCreatedAtDesc();

    @EntityGraph(attributePaths = "produto")
    Optional<AlertaEstoque> findWithProdutoById(Long id);
}
