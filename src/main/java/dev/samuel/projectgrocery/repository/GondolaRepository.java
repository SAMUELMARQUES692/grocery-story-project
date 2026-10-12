package dev.samuel.projectgrocery.repository;

import dev.samuel.projectgrocery.entity.Gondola;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GondolaRepository extends JpaRepository<Gondola, Long> {

    boolean existsGondolaByCodigo(String codigo);
}
