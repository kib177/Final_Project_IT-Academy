package by.finalproject.itacademy.userservice.service;

import by.finalproject.itacademy.userservice.model.entity.VerificationEntity;
import by.finalproject.itacademy.userservice.repository.VerificationCodeRepository;
import by.finalproject.itacademy.userservice.service.api.IVerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements IVerificationCodeService {
    private final VerificationCodeRepository verificationCodeRepository;

    @Override
    public void generateCode(String uMail) {
        String code = UUID.randomUUID().toString().substring(0, 6);
        VerificationEntity verifyCode = VerificationEntity.builder()
                .mail(uMail)
                .code(code)
                .build();
        verificationCodeRepository.save(verifyCode);
    }

    @Override
    public boolean validateCode(String mail, String code) {
        return verificationCodeRepository.existsByMailAndCode(mail, code);
    }

    @Override
    public void deleteCode(String mail) {
        verificationCodeRepository.deleteByMail(mail);
    }
}