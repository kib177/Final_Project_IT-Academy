package by.finalproject.itacademy.userservice.service.api;

public interface IEmailService {

    void sendVerificationEmail(String to, String code);
}
