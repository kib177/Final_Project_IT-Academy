package by.finalproject.itacademy.userservice.service;

import by.finalproject.itacademy.userservice.service.api.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements IEmailService {
    @Override
    public void sendVerificationEmail(String to, String code) {
        // In production, integrate with a real email service
        log.info("Sending verification email to {} with code {}", to, code);
        // Actual email sending implementation would go here
    }
}