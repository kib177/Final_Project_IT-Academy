package by.finalproject.itacademy.userservice.service;


import by.finalproject.itacademy.common.jwt.JwtTokenUtil;
import by.finalproject.itacademy.userservice.feign.AuditServiceClient;
import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.dto.UserLogDTO;
import by.finalproject.itacademy.userservice.model.dto.UserLogin;
import by.finalproject.itacademy.userservice.model.dto.UserRegistration;
import by.finalproject.itacademy.userservice.model.enums.UserStatus;
import by.finalproject.itacademy.userservice.service.api.ICabinetService;

import by.finalproject.itacademy.userservice.service.api.IVerificationCodeService;
import by.finalproject.itacademy.userservice.service.api.exception.CabinetException;
import by.finalproject.itacademy.userservice.model.entity.UserEntity;
import by.finalproject.itacademy.userservice.model.entity.VerificationEntity;
import by.finalproject.itacademy.userservice.service.mapper.UserMapper;
import by.finalproject.itacademy.userservice.repository.UserRepository;
import by.finalproject.itacademy.userservice.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static java.time.Instant.now;

@Service
@RequiredArgsConstructor
public class CabinetServiceImpl implements ICabinetService {
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final UserMapper userMapper;
    private final IVerificationCodeService verificationCodeService;
    private final AuditServiceClient auditClient;
    private final JwtTokenUtil jwtTokenUtil;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean registration(UserRegistration userRegistration) {

        if (userRepository.existsByMail(userRegistration.getMail())) {
            throw new CabinetException("Email already exists" + userRegistration.getMail());
        }
        
        UserEntity userEntity = userMapper.fromRegistrationDto(userRegistration);
        if(userEntity != null) {
            userEntity.setDtCreate(LocalDateTime.now());
            userEntity.setDtUpdate(
                    userEntity.getDtCreate());
            userRepository.save(userEntity);
        }
        verificationCodeService.generateCode(userRegistration.getMail());

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

        auditClient.logAction(UserLogDTO.builder()
                        .uuidUser(userEntity.getUuid())
                        .mail(userEntity.getMail())
                        .fio(userEntity.getFio())
                        .role(userEntity.getRole().toString())
                .build());
        /*if (!userEntity.getStatus().toString().equals("ACTIVATED")) {
            throw new RuntimeException("Account not activated");
        }*/

        return "Login successful for user: " + jwtTokenUtil.generateToken(userEntity.getUuid(),
                userEntity.getMail(), userEntity.getFio(), String.valueOf(userEntity.getRole()));
    }

    @Override
    public Optional<User> getById(UUID uuid) {
        UserEntity userEntity = userRepository.getByUuid(uuid)
                .orElseThrow(() -> new CabinetException("User not found" + uuid));
        return Optional.ofNullable(userMapper.toDto(userEntity));
    }
}
