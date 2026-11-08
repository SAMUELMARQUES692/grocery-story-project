package dev.samuel.projectgrocery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "produto_imagens")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"produtoLoja"})
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Builder
public class ProdutoImagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "produto_loja_id", nullable = false)
    private ProdutoLoja produtoLoja;

    @Column(nullable = false, length = 500)
    private String url;

    @Column(nullable = false)
    private Integer ordem;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

}
