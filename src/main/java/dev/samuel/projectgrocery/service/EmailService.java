package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.entity.Email;
import dev.samuel.projectgrocery.enums.EmailStatus;
import dev.samuel.projectgrocery.repository.EmailRepository;
import dev.samuel.projectgrocery.request.EstoqueBaixoEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailService {

    private static final DateTimeFormatter FORMATO_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;

    @Value("${app.alerta.email-remetente}")
    private String emailFrom;

    @Value("${app.alerta.email-destino}")
    private String emailTo;

    /**
     * Sem try/catch de proposito: se o envio falhar, a MailException sobe,
     * o RabbitMQ tenta de novo e, apos 3 falhas, manda a mensagem para a DLQ.
     */
    public void enviarAlertaEstoqueBaixo(EstoqueBaixoEvent event) {
        String assunto = "Estoque baixo: " + event.nomeProduto() + " (" + event.local().getLocal() + ")";

        String corpo = """
                O produto abaixo atingiu o estoque mínimo.

                Produto: %s
                Código de barras: %s
                Local: %s
                Quantidade atual: %d
                Quantidade mínima: %d
                Data/hora: %s

                Alerta nº %d
                """.formatted(
                event.nomeProduto(),
                event.codigoBarras(),
                event.local().getLocal(),
                event.quantidadeAtual(),
                event.minimo(),
                event.dataHora().format(FORMATO_DATA),
                event.alertaId());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
        message.setTo(emailTo);
        message.setSubject(assunto);
        message.setText(corpo);
        javaMailSender.send(message);

        // so chega aqui se o envio deu certo
        emailRepository.save(Email.builder()
                .emailFrom(emailFrom)
                .emailTo(emailTo)
                .emailSubject(assunto)
                .body(corpo)
                .statusEmail(EmailStatus.SENT)
                .enviadoEm(LocalDateTime.now())
                .build());

        log.info("E-mail do alerta {} enviado para {}", event.alertaId(), emailTo);
    }

}
