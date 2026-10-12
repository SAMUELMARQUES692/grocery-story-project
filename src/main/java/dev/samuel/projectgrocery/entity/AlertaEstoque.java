package dev.samuel.projectgrocery.entity;

import dev.samuel.projectgrocery.enums.LocalProduto;
import dev.samuel.projectgrocery.enums.StatusAlerta;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerta_estoque")
@ToString(exclude = {"produto"})
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlertaEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Enumerated(EnumType.STRING)
    @Column(name = "local_produto", nullable = false, length = 20)
    private LocalProduto local;

    @Column(name = "quantidade_no_momento", nullable = false)
    private Integer quantidadeNoMomento;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status_alerta", nullable = false, length = 20)
    private StatusAlerta status = StatusAlerta.ABERTO;

    @Column(name = "email_enviado", nullable = false)
    private boolean emailEnviado;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
