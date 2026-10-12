package dev.samuel.projectgrocery.repository;

import dev.samuel.projectgrocery.entity.ItemGondola;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemGondolaRepository extends JpaRepository<ItemGondola, Long> {

    Optional<ItemGondola> findByProdutoId(Long produtoId);
}
