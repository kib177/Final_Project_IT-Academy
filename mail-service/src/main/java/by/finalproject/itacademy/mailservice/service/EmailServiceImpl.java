package by.finalproject.itacademy.mailservice.service;

import by.finalproject.itacademy.mailservice.service.api.IEmailService;
import by.finalproject.itacademy.mailservice.service.api.ITemplateService;
import by.finalproject.itacademy.mailservice.service.exception.EmailSendingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {
    private final JavaMailSender mailSender;
    private final ITemplateService templateService;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.verification.base-url:http://localhost:8080}")
    private String baseUrl;

    @Override
    public void sendVerificationEmail(String to, String code) {
        log.info("Sending verification email to {} with code {}", to, code);

        try {
            sendHtmlVerificationEmail(to, code);
            log.info("Verification email successfully sent to {}", to);
        } catch (Exception e) {
            log.error("Failed to send verification email to {}", to, e);
            throw new EmailSendingException("Не удалось отправить email подтверждения", e);
        }
    }

    @Override
    public void sendWelcomeEmail(String to) {
        log.info("Sending welcome email to {}", to);

        try {
            sendHtmlWelcomeEmail(to);
            log.info("Welcome email successfully sent to {}", to);
        } catch (Exception e) {
            log.error("Failed to send welcome email to {}", to, e);
            throw new EmailSendingException("Не удалось отправить приветственное письмо", e);
        }
    }

    private void sendHtmlVerificationEmail(String to, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("Подтверждение регистрации");

            String htmlContent = createVerificationHtml(to, code);
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send HTML verification email to {}", to, e);
            throw new EmailSendingException("Не удалось отправить email подтверждения", e);
        }
    }

    private void sendHtmlWelcomeEmail(String to) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(to);
            helper.setSubject("Добро пожаловать!");

            String htmlContent = createWelcomeHtml();
            helper.setText(htmlContent, true);

            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send HTML welcome email to {}", to, e);
            throw new EmailSendingException("Не удалось отправить приветственное письмо", e);
        }
    }

    private String createVerificationHtml(String to, String code) {
        String verificationUrl = baseUrl + "/api/v1/cabinet/verification?mail=" + to + "&code=" + code;

        Map<String, String> variables = new HashMap<>();
        variables.put("code", code);
        variables.put("verificationUrl", verificationUrl);

        return templateService.loadTemplate("verification-email.html", variables);
    }

    private String createWelcomeHtml() {
        Map<String, String> variables = new HashMap<>();
        variables.put("baseUrl", baseUrl);

        return templateService.loadTemplate("welcome-email.html", variables);
    }
}