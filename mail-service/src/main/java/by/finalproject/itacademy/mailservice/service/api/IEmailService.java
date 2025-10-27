package by.finalproject.itacademy.mailservice.service.api;

public interface IEmailService {
    void sendVerificationEmail(String to, String code);
}
