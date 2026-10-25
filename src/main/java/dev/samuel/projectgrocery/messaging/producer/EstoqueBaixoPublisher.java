package dev.samuel.projectgrocery.messaging.producer;

import dev.samuel.projectgrocery.configuration.RabbitMQConfig;
import dev.samuel.projectgrocery.request.EstoqueBaixoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class EstoqueBaixoPublisher {

    private final RabbitTemplate rabbitTemplate;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void publicar(EstoqueBaixoEvent event) {
        try {
            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.ROUTING_KEY_ESTOQUE_BAIXO,
                    event);

            log.info("Evento de estoque baixo publicado: alerta {}, produto {}",
                    event.alertaId(), event.produtoId());
        }catch (AmqpException e) {
            // A movimentacao ja foi gravada; nao deixa a falha do RabbitMQ virar erro 500.
            // O alerta fica com emailEnviado = false e pode ser reenviado depois.
            log.error("Falha ao publicar o evento do alerta {}: {}", event.alertaId(), e.getMessage());
        }
    }

}
