package by.finalproject.itacademy.userservice.service;

import by.finalproject.itacademy.common.kafka.kafkaDTO.UserRegisteredEvent;
import by.finalproject.itacademy.common.kafka.kafkaDTO.UserVerifiedEvent;
import by.finalproject.itacademy.userservice.config.jwt.JwtTokenUtil;
import by.finalproject.itacademy.userservice.config.jwt.JwtUser;
import by.finalproject.itacademy.userservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.userservice.service.api.IAuditLogEventService;
import by.finalproject.itacademy.userservice.service.exception.*;
import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.dto.UserLogin;
import by.finalproject.itacademy.userservice.model.dto.UserRegistration;
import by.finalproject.itacademy.userservice.model.enums.UserStatus;
import by.finalproject.itacademy.userservice.service.api.ICabinetService;

import by.finalproject.itacademy.userservice.model.entity.UserEntity;
import by.finalproject.itacademy.userservice.service.mapper.UserMapper;
import by.finalproject.itacademy.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final IAuditLogEventService auditLogEventService;
    private final ValidService validService;// add interface
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String USER_REGISTERED_TOPIC = "user-registered-topic";
    private static final String USER_VERIFIED_TOPIC = "user-verified-topic";

    @Transactional
    @Override
    public void registration(UserRegistration userRegistration) {
        log.info("Registering new user with email: {}", userRegistration.getMail());

        validService.isValidEmail(userRegistration.getMail());

        if (userRepository.existsByMail(userRegistration.getMail())) {
            throw new UserServiceException("Пользователь с email " + userRegistration.getMail() + " уже существует");
        }
        try {
            userRegistration.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
            UserEntity userEntity = userMapper.fromRegistrationDto(userRegistration);

            userEntity.setDtCreate(LocalDateTime.now());
            userEntity.setDtUpdate(
                    userEntity.getDtCreate());
            userRepository.save(userEntity);

            UserRegisteredEvent event = new UserRegisteredEvent(
                    UUID.randomUUID(),
                    LocalDateTime.now(),
                    userRegistration.getMail()
            );

            kafkaTemplate.send(USER_REGISTERED_TOPIC, event);

            log.info("User {} successfully registry", userRegistration.getMail());
        } catch (UserServiceException e) {
            throw new UserServiceException("Ошибка при регистрации пользователя", e);
        }
    }

    @Transactional
    public void verifyUser(String mail, String code) {
        log.info("Verifying user with email: {}", mail);

        validService.isValidEmail(mail);

        /*if (verificationCodeService.validateCode(mail, code)) {
            throw new InvalidVerificationCodeException("Не верный код или mail");
        }*/
        UserEntity userEntity = userRepository.findByMail(mail)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
        try {
            userEntity.setStatus(UserStatus.ACTIVATED);
            userRepository.save(userEntity);

            UserVerifiedEvent event = new UserVerifiedEvent(
                    UUID.randomUUID(),
                    LocalDateTime.now(),
                    mail,
                    userEntity.getUuid(),
                    code
            );

            kafkaTemplate.send(USER_VERIFIED_TOPIC, event);

            log.info("User {} successfully verified", mail);
        } catch (VerificationCodeException e) {
            throw new VerificationCodeException("Не удачная попытка верификации", e);
        }
    }

    @Override
    public String login(UserLogin userLogin) {
        log.info("Login attempt for email: {}", userLogin.getMail());

        /*validService.isValidEmail(userLogin.getMail());*/

        UserEntity userEntity = userRepository.findByMail(userLogin.getMail())
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

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
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        auditLogEventService.sendAudit(getCurrentUser(),
                "Информация о себе",
                userEntity.getUuid(),
                EssenceTypeEnum.USER);

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
