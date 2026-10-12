package dev.samuel.projectgrocery.repository;

import dev.samuel.projectgrocery.entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
}
