package com.example.finalProject.service;

import com.example.finalProject.dto.UserCreate;
import com.example.finalProject.dto.UserLogin;
import com.example.finalProject.dto.UserRegistration;
import com.example.finalProject.dto.enums.UserStatus;
import com.example.finalProject.service.api.ICabinetService;
import com.example.finalProject.storage.entity.UserEntity;
import com.example.finalProject.storage.entity.VerificationEntity;
import com.example.finalProject.storage.repository.UserRepository;
import com.example.finalProject.storage.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CabinetServiceImpl implements ICabinetService {
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    //private final PasswordEncoder passwordEncoder;

    @Override
    public boolean registration(UserRegistration userRegistration) {
        if (userRepository.existsByMail(userRegistration.getMail())) {
            throw new RuntimeException("Email already exists");
        }

        UserEntity userEntity = UserEntity.builder()
                .mail(userRegistration.getMail())
                .fio(userRegistration.getFio())
                .password(userRegistration.getPassword())
                .build();
        userRepository.save(userEntity);

        String code = UUID.randomUUID().toString().substring(0, 6);
        VerificationEntity verificationCode = VerificationEntity.builder()
                .mail(userRegistration.getMail())
                .code(code)
                .build();
        verificationCodeRepository.save(verificationCode);

        System.out.println("Verification code for " + userRegistration.getMail() + ": " + code);
        return true;
    }

    @Transactional
    public boolean verifyUser(String mail, String code) {
        Optional<VerificationEntity> storedCode = verificationCodeRepository.findByMailAndCode(mail, code);
        if (storedCode.isPresent()) {
            UserEntity user = userRepository.findByMail(mail)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            user.setStatus(UserStatus.ACTIVATED);
            userRepository.save(user);
            verificationCodeRepository.deleteByMail(mail);
            return true;
        }
        return false;
    }



    @Override
    public String login(UserLogin userLogin) {
        UserEntity userEntity = userRepository.findByMail(userLogin.getMail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!userLogin.getPassword().equals(userEntity.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        /*if (!userEntity.getStatus().toString().equals("ACTIVATED")) {
            throw new RuntimeException("Account not activated");
        }*/

        return "Login successful for user: " + userEntity.getFio();
    }



    @Override
    public boolean update(UUID uuid, long dt_update, UserCreate userCreate) {
        return false;
    }

   /* @Override
    public User getByUUID(UUID uuid) {
        return null;
    }*/
}
