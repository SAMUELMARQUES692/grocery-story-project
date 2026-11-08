package dev.samuel.projectgrocery.repository;

import dev.samuel.projectgrocery.entity.ProdutoImagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProdutoImagemRepository extends JpaRepository<ProdutoImagem, Long> {

    List<ProdutoImagem> findByProdutoLojaIdOrderByOrdem(Long produtoLojaId);

    Optional<ProdutoImagem> findByIdAndProdutoLojaId(Long id, Long produtoLojaId);

    @Query("SELECT COALESCE(MAX(i.ordem), -1) FROM ProdutoImagem i WHERE i.produtoLoja.id = :produtoLojaId")
    Integer maiorOrdem(@Param("produtoLojaId") Long produtoLojaId);
}
