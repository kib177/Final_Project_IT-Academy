package com.example.finalProject.service;

import com.example.finalProject.dto.User;
import com.example.finalProject.dto.UserCreate;
import com.example.finalProject.dto.UserRegistration;
import com.example.finalProject.service.api.ICabinetService;
import com.example.finalProject.storage.entity.UserEntity;
import com.example.finalProject.storage.repository.UserRepository;
import com.example.finalProject.storage.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CabinetServiceImpl implements ICabinetService {
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;

    @Override
    public boolean create(UserCreate userCreate) {
        return false;
    }

    @Override
    public boolean registration() {


        // Map UserRegistration to UserEntity
        UserEntity userEntity = UserEntity.builder()
                .mail("test@example.com")
                .fio("123123")
                .password("123213123")
                .build();
        userRepository.save(userEntity);

        /*// Generate verification code
        String code = UUID.randomUUID().toString().substring(0, 6);
        VerificationCode verificationCode = VerificationCode.builder()
                .mail(userRegistration.getMail())
                .code(code)
                .build();
        verificationCodeRepository.save(verificationCode);*/

        // Simulate email sending

        return true;
    }

    @Override
    public boolean update(UUID uuid, long dt_update, UserCreate userCreate) {
        return false;
    }

    @Override
    public User get(int page, int size) {
        return null;
    }

    @Override
    public User getByUUID(UUID uuid) {
        return null;
    }
}
