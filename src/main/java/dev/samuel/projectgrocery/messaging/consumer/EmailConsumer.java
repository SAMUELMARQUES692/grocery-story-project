package dev.samuel.projectgrocery.messaging.consumer;

import dev.samuel.projectgrocery.configuration.RabbitMQConfig;
import dev.samuel.projectgrocery.request.EstoqueBaixoEvent;
import dev.samuel.projectgrocery.service.AlertaEstoqueService;
import dev.samuel.projectgrocery.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;
    private final AlertaEstoqueService alertaEstoqueService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ESTOQUE_BAIXO)
    public void consumirEstoqueBaixo(@Payload EstoqueBaixoEvent event) {
        log.info("Mensagem recebida: alerta {}, produto {}", event.alertaId(), event.produtoId());

        emailService.enviarAlertaEstoqueBaixo(event);
        alertaEstoqueService.marcarEmailEnviado(event.alertaId());
    }
}
