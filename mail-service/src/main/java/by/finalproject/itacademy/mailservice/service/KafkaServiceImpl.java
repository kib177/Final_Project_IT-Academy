package by.finalproject.itacademy.mailservice.service;

import by.finalproject.itacademy.common.kafka.kafkaDTO.UserRegisteredEvent;
import by.finalproject.itacademy.common.kafka.kafkaDTO.UserVerifiedEvent;
import by.finalproject.itacademy.mailservice.service.api.IEmailService;
import by.finalproject.itacademy.mailservice.service.api.IKafkaService;
import by.finalproject.itacademy.mailservice.service.api.IVerificationCodeService;
import by.finalproject.itacademy.mailservice.service.exception.EmailSendingException;
import by.finalproject.itacademy.mailservice.service.exception.InvalidVerificationCodeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements IKafkaService {

    private final IVerificationCodeService verificationCodeService;
    private final IEmailService emailService;

        @KafkaListener(topics = "user-registered-topic")
        @Override
        public void handleUserRegistered(UserRegisteredEvent event) {
            log.info("Received user registered event for email: {}", event.getEmail());

            try {

                String verificationCode = verificationCodeService.generateCode(event.getEmail());
                emailService.sendVerificationEmail(event.getEmail(), verificationCode);

                log.info("Verification email sent successfully to: {}", event.getEmail());
            } catch (Exception e) {
                log.error("Failed to process user registered event for email: {}", event.getEmail(), e);
                throw new EmailSendingException("Failed to process user registered event for email: " + event.getEmail(), e);

            }
        }

        @KafkaListener(topics = "user-verified-topic")
        @Override
        public void handleUserVerified(UserVerifiedEvent event) {
            log.info("Received user verified event for email: {}", event.getEmail());

            if(!verificationCodeService.validateCode(event.getEmail(), event.getCode())) {
                throw new InvalidVerificationCodeException("Invalid verification mail or code");
            }

            try {

                //emailService.sendWelcomeEmail(event.getEmail());

                log.info("Welcome email sent successfully to: {}", event.getEmail());
            } catch (Exception e) {
                log.error("Failed to process user verified event for email: {}", event.getEmail(), e);
            }
        }
}