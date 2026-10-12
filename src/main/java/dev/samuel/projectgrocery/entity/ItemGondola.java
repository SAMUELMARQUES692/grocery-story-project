package dev.samuel.projectgrocery.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "item_gondolas",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_item_gondola_produto",
                columnNames = {"gondola_id", "produto_id"}))
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"produto", "gondola"})
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Builder
public class ItemGondola {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gondola_id", nullable = false)
    private Gondola gondola;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer prateleira;

    @Column(nullable = false, name = "quantidade_atual")
    private Integer quantidadeAtual;

    @Column(nullable = false, name = "capacidade_maxima")
    private Integer capacidadeMaxima;

    @Column(nullable = false, name = "capacidade_minima")
    private Integer capacidadeMinima;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
