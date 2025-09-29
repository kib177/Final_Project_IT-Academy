package by.finalproject.itacademy.userservice.service;


import by.finalproject.itacademy.common.exception.InternalServerErrorException;
import by.finalproject.itacademy.common.exception.StructuredValidationException;
import by.finalproject.itacademy.common.jwt.JwtTokenUtil;
import by.finalproject.itacademy.common.jwt.JwtUser;
import by.finalproject.itacademy.common.model.dto.StructuredErrorResponse;
import by.finalproject.itacademy.userservice.model.dto.User;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CabinetServiceImpl implements ICabinetService {
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final UserMapper userMapper;
    private final IVerificationCodeService verificationCodeService;
    private final JwtTokenUtil jwtTokenUtil;
    private final ValidService validService;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public void registration(UserRegistration userRegistration)
            throws BadRequestException {

        log.info("Registering new user with email: {}", userRegistration.getMail());

        if (userRepository.existsByMail(userRegistration.getMail())) {
            throw new BadRequestException("Пользователь с электронной почтой " + userRegistration.getMail() + " уже существует");
        }

        validService.validateRegistrationData(userRegistration);

        try {
            UserEntity userEntity = userMapper.fromRegistrationDto(userRegistration);
            if (userEntity != null) {
                userEntity.setDtCreate(LocalDateTime.now());
                userEntity.setDtUpdate(
                        userEntity.getDtCreate());
                userRepository.save(userEntity);
            }
            verificationCodeService.generateCode(userRegistration.getMail());

        } catch (DataAccessException e) {
            log.error("Database error during user registration: {}", e.getMessage());
            throw new InternalServerErrorException("Ошибка при регистрации пользователя");
        }
    }

    @Transactional
    public void verifyUser(String mail, String code) throws BadRequestException {
        log.info("Verifying user with email: {}", mail);

        if (code == null || code.trim().isEmpty()) {
            throw new BadRequestException("Код верификации не может быть пустым");
        }
        if (mail == null || mail.trim().isEmpty()) {
            throw new BadRequestException("Электронная почта не может быть пустой");
        }

        UserEntity userEntity = userRepository.findByMail(mail)
                .orElseThrow(() -> new BadRequestException("User not found" + mail));
        if (userEntity.getStatus() != UserStatus.WAITING_ACTIVATION) {
            throw new BadRequestException("Пользователь уже активирован или деактивирован");
        }

        Optional<VerificationEntity> verification = verificationCodeRepository.findByMailAndCode(mail, code);
        if (verification.isEmpty()) {
            throw new BadRequestException("Неверный код верификации");
        }

        try {
            userEntity.setStatus(UserStatus.ACTIVATED);
            userRepository.save(userEntity);
            verificationCodeRepository.deleteByMail(mail);
            log.info("User {} successfully verified", mail);

        } catch (DataAccessException e) {
            log.error("Database error during user verification: {}", e.getMessage());
            throw new InternalServerErrorException("Ошибка при верификации пользователя");
        }
    }

    @Override
    public String login(UserLogin userLogin) throws BadRequestException {
        log.info("Login attempt for email: {}", userLogin.getMail());

        if (userLogin.getMail() == null || userLogin.getMail().trim().isEmpty()) {
            throw new BadRequestException("Электронная почта не может быть пустой");
        }
        if (userLogin.getPassword() == null || userLogin.getPassword().isEmpty()) {
            throw new BadRequestException("Пароль не может быть пустым");
        }
        UserEntity userEntity = userRepository.findByMail(userLogin.getMail())
                .orElseThrow(() -> new BadRequestException("Некорректные данные"));
        if (!userEntity.getPassword().equals(userLogin.getPassword())) {
            throw new BadRequestException("Некорректные данные");
        }

        try {
            return "Login successful for user: " + jwtTokenUtil.generateToken(userEntity.getUuid(),
                    userEntity.getMail(), userEntity.getFio(), String.valueOf(userEntity.getRole()));
        } catch (Exception e) {
            log.error("Token generation error: {}", e.getMessage());
            throw new InternalServerErrorException("Ошибка при генерации токена доступа");
        }
    }

    @Override
    public User getAboutSelf() throws BadRequestException {
        log.info("Getting current user info: {}", getCurrentUserUuid());
        UserEntity userEntity = userRepository.getByUuid(getCurrentUserUuid())
                .orElseThrow(() -> new BadRequestException("User not found"));
        return userMapper.toDto(userEntity);
    }

    public UUID getCurrentUserUuid() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return jwtUser.userId();
    }
}
