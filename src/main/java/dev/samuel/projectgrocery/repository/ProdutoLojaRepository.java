package dev.samuel.projectgrocery.repository;

import dev.samuel.projectgrocery.entity.ProdutoLoja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutoLojaRepository extends JpaRepository<ProdutoLoja, Long> {

    Optional<ProdutoLoja> findByProdutoId(Long produtoId);

}
