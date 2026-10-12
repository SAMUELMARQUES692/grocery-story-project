package dev.samuel.projectgrocery.repository;

import dev.samuel.projectgrocery.entity.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    boolean existsByCnpj(String cnpj);
    boolean existsByEmail(String email);
}
