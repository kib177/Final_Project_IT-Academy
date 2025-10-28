package by.finalproject.itacademy.mailservice.service;

import by.finalproject.itacademy.mailservice.model.entity.VerificationEntity;
import by.finalproject.itacademy.mailservice.repository.VerificationRepository;

import by.finalproject.itacademy.mailservice.service.api.IVerificationCodeService;
import by.finalproject.itacademy.mailservice.service.exception.VerificationCodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CodeServiceImpl implements IVerificationCodeService {
    private final VerificationRepository emailRepository;

    @Override
    public String generateCode(String mail) {
        try {
            String code = UUID.randomUUID().toString().substring(0, 6);
            VerificationEntity verifyCode = VerificationEntity.builder()
                    .mail(mail)
                    .code(code)
                    .build();
            emailRepository.save(verifyCode);
            return code;
        }catch (VerificationCodeException e){
            throw new VerificationCodeException("Ошибка при генерации или сохранении кода");
        }
    }

    @Override
    public boolean validateCode(String mail, String code) {
        return emailRepository.existsByMailAndCode(mail, code);
    }

    @Override
    public void deleteCode(String mail) {
        try {
            emailRepository.deleteByMail(mail);
        }catch (VerificationCodeException e){
            throw new VerificationCodeException("Ошибка при удалении кода верификации");
        }
    }
}