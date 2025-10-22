package by.finalproject.itacademy.userservice.service.api;

import java.util.concurrent.CompletableFuture;

public interface IEmailService {
    CompletableFuture<Boolean> sendVerificationEmail(String recipientEmail, String verificationCode);
    CompletableFuture<Boolean> sendWelcomeEmail(String recipientEmail, String username);
    CompletableFuture<Boolean> sendPasswordResetEmail(String recipientEmail, String resetToken);
    void sendSimpleTextEmail(String recipientEmail, String subject, String textContent);
}
