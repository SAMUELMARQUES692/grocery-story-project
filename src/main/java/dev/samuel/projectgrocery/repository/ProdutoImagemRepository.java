package dev.samuel.projectgrocery.repository;

import dev.samuel.projectgrocery.entity.ProdutoImagem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoImagemRepository extends JpaRepository<ProdutoImagem, Long> {
}
