package com.example.finalProject.service;

import com.example.finalProject.dto.User;
import com.example.finalProject.dto.UserLogin;
import com.example.finalProject.dto.UserRegistration;
import com.example.finalProject.dto.enums.UserStatus;
import com.example.finalProject.service.api.ICabinetService;
import com.example.finalProject.service.api.exception.CabinetException;
import com.example.finalProject.storage.entity.UserEntity;
import com.example.finalProject.storage.entity.VerificationEntity;
import com.example.finalProject.storage.repository.UserRepository;
import com.example.finalProject.storage.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CabinetServiceImpl implements ICabinetService {
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean registration(UserRegistration userRegistration) {

        if (userRepository.existsByMail(userRegistration.getMail())) {
            throw new CabinetException("Email already exists" + userRegistration.getMail());
        }

        UserEntity userEntity = UserEntity.builder()
                .mail(userRegistration.getMail())
                .fio(userRegistration.getFio())
                .password(userRegistration.getPassword())
                .build();
        userRepository.save(userEntity);

        String code = UUID.randomUUID().toString().substring(0, 6);
        VerificationEntity verifyCode = VerificationEntity.builder()
                .mail(userRegistration.getMail())
                .code(code)
                .build();
        verificationCodeRepository.save(verifyCode);

        System.out.println("Verification code for " + userRegistration.getMail() + ": " + code);
        return true;
    }

    @Transactional
    public boolean verifyUser(String mail, String code) {
        Optional<VerificationEntity> verifyCode = verificationCodeRepository.findByMailAndCode(mail, code);

        if (verifyCode.isPresent()) {
            UserEntity user = userRepository.findByMail(mail)
                    .orElseThrow(() -> new CabinetException("User not found" + mail));
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
                .orElseThrow(() -> new CabinetException("User not found" + userLogin.getMail()));

        if (!userLogin.getPassword().equals(userEntity.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        /*if (!userEntity.getStatus().toString().equals("ACTIVATED")) {
            throw new RuntimeException("Account not activated");
        }*/

        return "Login successful for user: " + userEntity.getFio();
    }

    @Override
    public Optional<User> getById(UUID uuid) {
        UserEntity userEntity = userRepository.getByUuid(uuid)
                .orElseThrow(() -> new CabinetException("User not found" + uuid));
        return Optional.ofNullable(User.builder()
                .uuid(userEntity.getUuid())
                .dt_create(userEntity.getDt_create())
                .dt_update(userEntity.getDt_update())
                .mail(userEntity.getMail())
                .fio(userEntity.getFio())
                .role(userEntity.getRole())
                .status(userEntity.getStatus())
                .build());
    }
}
