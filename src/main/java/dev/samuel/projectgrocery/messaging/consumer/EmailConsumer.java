package dev.samuel.projectgrocery.messaging.consumer;

import dev.samuel.projectgrocery.entity.Email;
import dev.samuel.projectgrocery.enums.EmailStatus;
import dev.samuel.projectgrocery.request.EstoqueBaixoEvent;
import dev.samuel.projectgrocery.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailConsumer {

    private final EmailService emailService;

    @Value("${app.alerta.email-destino}")
    private String emailTo;

    @RabbitListener(queues = "estoque.baixo.queue")
    public void enviarAlertaEstoqueBaixo(@Payload EstoqueBaixoEvent event) {
        Email email = Email.builder()
                .emailTo(emailTo)
                .emailSubject("Alerta de Estoque")
                .body("Olá, o produto " + event.nomeProduto() + " que teve uma movimentação as " + event.dataHora() + " está a baixo do estoque minimo na posição " + event.local() +
                        "Detalhes \n\n" + "Quantidade Atual: " + event.quantidadeAtual() + "\n\nQuantidade Minima: " + event.minimo() + "\n\nCodigo de Barras" + event.codigoBarras() +
                        "\n\nID Produto: " + event.produtoId() + "\n\nID Alerta: " + event.alertaId()
                )
                .statusEmail(EmailStatus.PENDING)
                .build();

        emailService.sendEmail(email);
    }
}
