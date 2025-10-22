package by.finalproject.itacademy.userservice.service;

import by.finalproject.itacademy.userservice.service.api.IEmailService;
import by.finalproject.itacademy.userservice.service.exception.EmailSendingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender mailSender;
   /* private final TemplateEngine templateEngine;*/

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.verification.base-url:http://localhost:8080}")
    private String baseUrl;

    /*@Value("${app.verification.enable-html:true}")
    private boolean enableHtml;*/

    @Override
    public void sendVerificationEmail(String to, String code) {
        log.info("Sending verification email to {} with code {}", to, code);

        try {
            /*
        }
            if (enableHtml) {
                sendHtmlVerificationEmail(to, code);
            } else {*/
                sendPlainTextVerificationEmail(to, code);

            log.info("Verification email successfully sent to {}", to);
        } catch (Exception e) {
            log.error("Failed to send verification email to {}", to, e);
            throw new EmailSendingException("Не удалось отправить email подтверждения", e);
        }
    }


    private void sendPlainTextVerificationEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Подтверждение регистрации");
        message.setText(createPlainTextEmailContent(to, code));

        mailSender.send(message);
    }

    /**
     * Создание текстового содержимого email
     */
    private String createPlainTextEmailContent(String to, String code) {
        return String.format(
                "Здравствуйте!\n\n" +
                        "Для завершения регистрации вашего аккаунта с email %s, " +
                        "пожалуйста, используйте следующий код подтверждения:\n\n" +
                        "Код: %s\n\n" +
                        "Или перейдите по ссылке:\n" +
                        "%s/api/v1/cabinet/verification?mail=%s&code=%s\n\n" +
                        "Если вы не регистрировались в нашем сервисе, проигнорируйте это письмо.\n\n" +
                        "С уважением,\n" +
                        "Команда сервиса",
                to, code, baseUrl, to, code
        );
    }
}