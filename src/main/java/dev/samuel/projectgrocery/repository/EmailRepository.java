package dev.samuel.projectgrocery.repository;

import dev.samuel.projectgrocery.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
}
