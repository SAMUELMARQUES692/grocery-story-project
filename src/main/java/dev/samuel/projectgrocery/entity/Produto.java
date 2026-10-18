package dev.samuel.projectgrocery.entity;

import dev.samuel.projectgrocery.enums.UnidadeMedida;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "produtos")
@Getter
@Setter
@ToString(exclude = {"categoria", "fornecedor"})
@EqualsAndHashCode(of = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_de_barras", length = 14, nullable = false, unique = true)
    private String codigoBarras;

    @Column(length = 100, nullable = false)
    private String nome;

    @Column(name = "codigo_do_produto", length = 4, nullable = false, unique = true)
    private String codigoProduto;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal precoCusto;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal precoVenda;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private UnidadeMedida unidadeMedida;

    @Builder.Default
    @Column(nullable = false)
    private boolean ativo = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Fornecedor fornecedor;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
