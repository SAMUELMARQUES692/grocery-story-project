package dev.samuel.projectgrocery.repository;

import dev.samuel.projectgrocery.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    boolean existsByCodigoBarras(String codigoBarras);
}
