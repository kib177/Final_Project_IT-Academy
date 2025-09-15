package by.finalproject.itacademy.userservice.service;


import by.finalproject.itacademy.userservice.dto.User;
import by.finalproject.itacademy.userservice.dto.UserLogin;
import by.finalproject.itacademy.userservice.dto.UserRegistration;
import by.finalproject.itacademy.userservice.dto.enums.UserStatus;
import by.finalproject.itacademy.userservice.service.api.ICabinetService;

import by.finalproject.itacademy.userservice.service.api.exception.CabinetException;
import by.finalproject.itacademy.userservice.storage.entity.UserEntity;
import by.finalproject.itacademy.userservice.storage.entity.VerificationEntity;
import by.finalproject.itacademy.userservice.storage.mapper.UserMapper;
import by.finalproject.itacademy.userservice.storage.repository.UserRepository;
import by.finalproject.itacademy.userservice.storage.repository.VerificationCodeRepository;
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
    private final UserMapper userMapper;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean registration(UserRegistration userRegistration) {

        if (userRepository.existsByMail(userRegistration.getMail())) {
            throw new CabinetException("Email already exists" + userRegistration.getMail());
        }
        
        UserEntity userEntity = userMapper.fromRegistrationDto(userRegistration);
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
            UserEntity userEntity = userRepository.findByMail(mail)
                    .orElseThrow(() -> new CabinetException("User not found" + mail));
            userEntity.setStatus(UserStatus.ACTIVATED);
            userRepository.save(userEntity);
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
        return Optional.ofNullable(userMapper.toDto(userEntity));
    }
}
