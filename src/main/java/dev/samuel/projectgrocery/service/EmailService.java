package dev.samuel.projectgrocery.service;

import dev.samuel.projectgrocery.entity.Email;
import dev.samuel.projectgrocery.enums.EmailStatus;
import dev.samuel.projectgrocery.repository.EmailRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmailService {


    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;

    @Value("${app.alerta.email-remetente}")
    private String emailFrom;

    @Value("${app.alerta.email-destino}")
    private String emailTo;

    @Transactional
    public void sendEmail(Email email) {
        email.setEmailFrom(emailFrom);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailFrom);
            message.setTo(emailTo);
            message.setSubject(email.getEmailSubject());
            message.setText(email.getBody());
            javaMailSender.send(message);
            email.setStatusEmail(EmailStatus.SENT);
            email.setEnviadoEm(LocalDateTime.now());

        } catch (Exception exception) {
            email.setStatusEmail(EmailStatus.FAILED);
            log.error("Erro ao enviar email para {}: {}", email.getEmailTo(), exception.getMessage());
        }

        emailRepository.save(email);
    }

}
