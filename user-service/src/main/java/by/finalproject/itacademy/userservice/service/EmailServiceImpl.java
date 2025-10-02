package by.finalproject.itacademy.userservice.service;

import by.finalproject.itacademy.userservice.service.api.IEmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements IEmailService {
    @Override
    public void sendVerificationEmail(String to, String code) {

        log.info("Sending verification email to {} with code {}", to, code);

    }
}