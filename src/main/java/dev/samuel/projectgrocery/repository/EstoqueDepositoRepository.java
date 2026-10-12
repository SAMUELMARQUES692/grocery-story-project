package dev.samuel.projectgrocery.repository;

import dev.samuel.projectgrocery.entity.EstoqueDeposito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstoqueDepositoRepository extends JpaRepository<EstoqueDeposito, Long> {

    boolean existsByProdutoId(Long produtoId);

    Optional<EstoqueDeposito> findByProdutoId(Long produtoId);

}
