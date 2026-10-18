package dev.samuel.projectgrocery.repository;

import dev.samuel.projectgrocery.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByCodigoBarras(String codigoBarras);

    @Query(value = "SELECT nextval('codigo_produto_seq')", nativeQuery = true)
    Long proximoCodigo();
}
