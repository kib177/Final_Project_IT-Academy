package by.finalproject.itacademy.userservice.service;

import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.userservice.service.exception.*;
import by.finalproject.itacademy.common.jwt.JwtTokenUtil;
import by.finalproject.itacademy.common.jwt.JwtUser;
import by.finalproject.itacademy.userservice.feign.AuditServiceClient;
import by.finalproject.itacademy.userservice.model.dto.AuditEventRequest;
import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.dto.UserLogin;
import by.finalproject.itacademy.userservice.model.dto.UserRegistration;
import by.finalproject.itacademy.userservice.model.enums.UserStatus;
import by.finalproject.itacademy.userservice.service.api.ICabinetService;

import by.finalproject.itacademy.userservice.service.api.IVerificationCodeService;
import by.finalproject.itacademy.userservice.model.entity.UserEntity;
import by.finalproject.itacademy.userservice.service.mapper.UserMapper;
import by.finalproject.itacademy.userservice.repository.UserRepository;
import by.finalproject.itacademy.userservice.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CabinetServiceImpl implements ICabinetService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final IVerificationCodeService verificationCodeService;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuditServiceClient auditServiceClient;

    @Transactional
    @Override
    public void registration(UserRegistration userRegistration) {

        log.info("Registering new user with email: {}", userRegistration.getMail());

        if (userRepository.existsByMail(userRegistration.getMail())) {
            throw new UserNotFoundException("Пользователь с email " + userRegistration.getMail() + " уже существует");
        }
        try {
            userRegistration.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
            UserEntity userEntity = userMapper.fromRegistrationDto(userRegistration);

            userEntity.setDtCreate(LocalDateTime.now());
            userEntity.setDtUpdate(
                    userEntity.getDtCreate());
            userRepository.save(userEntity);

            verificationCodeService.generateCode(userRegistration.getMail());
        } catch (Exception e) {
            throw new UserServiceException("Ошибка при регистрации пользователя", e);
        }
    }

    @Transactional
    public void verifyUser(String mail, String code) {
        log.info("Verifying user with email: {}", mail);

        if (verificationCodeService.validateCode(mail, code)) {
            throw new UserNotFoundException("Не верный код или mail");
        }
        try {
            UserEntity userEntity = userRepository.findByMail(mail);
            userEntity.setStatus(UserStatus.ACTIVATED);
            userRepository.save(userEntity);
            verificationCodeService.deleteCode(mail);
            log.info("User {} successfully verified", mail);
        }catch (Exception e) {
            throw new VerificationCodeException("Не удачная попытка верификации", e);
        }
        log.info("User {} is not verified", mail);

    }

    @Override
    public String login(UserLogin userLogin) {
        log.info("Login attempt for email: {}", userLogin.getMail());

        UserEntity userEntity = userRepository.findByMail(userLogin.getMail());

        if (userEntity == null) {
            throw new InvalidCredentialsException("Неверный email или пароль");
        }

        if (!passwordEncoder.matches(userLogin.getPassword(), userEntity.getPassword())) {
            throw new InvalidCredentialsException("Неверный email или пароль");
        }

        if (userEntity.getStatus() == UserStatus.WAITING_ACTIVATION) {
            throw new EntityAlreadyExistsException("Попытка входа не верифицированного пользователя");
        }

        return "Login successful for user: " +
                jwtTokenUtil
                        .generateToken(userEntity.getUuid(),
                                userEntity.getMail(),
                                userEntity.getFio(),
                                String.valueOf(userEntity.getRole()));

    }

    @Override
    public User getAboutSelf() {
        log.info("Getting current user info: {}", getCurrentUserUuid());
        UserEntity userEntity = userRepository.getByUuid(getCurrentUserUuid())
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));;

        auditServiceClient.logEvent(
                AuditEventRequest.builder()
                        .jwtUser(getCurrentUser())
                        .userInfo("Информация о себе")
                        .essenceId(userEntity.getUuid())
                        .type(EssenceTypeEnum.USER)
                        .build());
        return userMapper.toDto(userEntity);
    }

    public UUID getCurrentUserUuid() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtUser.userId();
    }

    public JwtUser getCurrentUser() {
        return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
