package by.finalproject.itacademy.userservice.service;

import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.common.exception.InternalServerErrorException;
import by.finalproject.itacademy.common.jwt.JwtUser;
import by.finalproject.itacademy.userservice.feign.AuditServiceClient;
import by.finalproject.itacademy.userservice.model.dto.AuditEventRequest;
import by.finalproject.itacademy.userservice.model.dto.PageOfUser;
import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.dto.UserCreate;
import by.finalproject.itacademy.userservice.service.api.IUserService;
import by.finalproject.itacademy.userservice.service.api.IVerificationCodeService;
import by.finalproject.itacademy.userservice.service.api.exception.CabinetException;
import by.finalproject.itacademy.userservice.model.entity.UserEntity;
import by.finalproject.itacademy.userservice.service.mapper.PageMapper;
import by.finalproject.itacademy.userservice.service.mapper.UserMapper;
import by.finalproject.itacademy.userservice.repository.UserRepository;
import by.finalproject.itacademy.userservice.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {
    private final IVerificationCodeService verificationCodeService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PageMapper pageMapper;
    private final VerificationCodeRepository verificationCodeRepository;
    private final PasswordEncoder passwordEncoder;
    private final ValidService validService;
    private final AuditServiceClient auditServiceClient;

    @Transactional
    @Override
    public void create(UserCreate userCreate) throws BadRequestException {
        log.info("Creating new user with email: {}", userCreate.getMail());

        if (userRepository.existsByMail(userCreate.getMail())) {
            throw new BadRequestException("Пользователь с электронной почтой " + userCreate.getMail() + " уже существует");
        }

        try {
            userCreate.setPassword(passwordEncoder.encode(userCreate.getPassword()));
            UserEntity userEntity = userMapper.fromCreateDto(userCreate);
            if (userEntity != null) {
                userEntity.setDtCreate(LocalDateTime.now());
                userEntity.setDtUpdate(
                        userEntity.getDtCreate());
                userRepository.save(userEntity);
            }
            verificationCodeService.generateCode(userCreate.getMail());
            auditServiceClient.logEvent(
                    AuditEventRequest.builder()
                            .jwtUser(getCurrentUser())
                            .userInfo("Создан новый пользователь ")
                            .essenceId(userEntity.getUuid())
                            .type(EssenceTypeEnum.USER)
                            .build());
        } catch (DataAccessException e) {
            log.error("Database error while creating user: {}", e.getMessage());
            throw new InternalServerErrorException("Ошибка при создании пользователя в базе данных");
        }
    }

    @Override
    public User getById(UUID uuid) throws BadRequestException {
        log.info("Getting user by UUID: {}", uuid);

        if (!validService.isValidUuid(String.valueOf(uuid))) {
            throw new BadRequestException("Некорректный формат UUID: " + uuid);
        }
        UserEntity userEntity = userRepository.getByUuid(uuid)
                    .orElseThrow(() -> new BadRequestException("User not found" + uuid));

        return userMapper.toDto(userEntity);
    }

    @Override
    public PageOfUser getUsersPage(Pageable pageable) {
        log.info("Getting users page: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        try {
            Page<UserEntity> entityPage = userRepository.findAll(pageable);
            return pageMapper.toPageOfUser(entityPage, userMapper);
        } catch (DataAccessException e) {
            log.error("Database error while fetching users page: {}", e.getMessage());
            throw new InternalServerErrorException("Ошибка при получении списка пользователей");
        }
    }

    @Transactional
    @Override
    public void updateUser(UUID uuid, Long dtUpdate, UserCreate userCreate) throws BadRequestException {
        log.info("Updating user with UUID: {}", uuid);

        if (!validService.isValidUuid(String.valueOf(uuid))) {
            throw new BadRequestException("Некорректный формат UUID: " + uuid);
        }

        UserEntity userEntity = userRepository.getByUuid(uuid)
                    .orElseThrow(() -> new BadRequestException("Пользователь не найден" + uuid));

        if (!userEntity.getDtUpdate().equals(dtUpdate)) {
            throw new BadRequestException("Конфликт версий данных. Получите актуальную версию пользователя");
        }

        if (!userEntity.getMail().equals(userCreate.getMail()) &&
                userRepository.existsByMail(userCreate.getMail())) {
            throw new BadRequestException("Пользователь с электронной почтой " + userCreate.getMail() + " уже существует");
        }

        try {
        userEntity.setFio(userCreate.getFio());
        userEntity.setMail(userCreate.getMail());
        if (userCreate.getPassword() != null && !userCreate.getPassword().isEmpty()) {
            userEntity.setPassword(passwordEncoder.encode(userCreate.getPassword()));
        }

        userRepository.save(userEntity);
            auditServiceClient.logEvent(
                    AuditEventRequest.builder()
                            .jwtUser(getCurrentUser())
                            .userInfo("Изменен пользователь")
                            .essenceId(userEntity.getUuid())
                            .type(EssenceTypeEnum.USER)
                            .build());

        } catch (DataAccessException e) {
            log.error("Database error while updating user: {}", e.getMessage());
            throw new InternalServerErrorException("Ошибка при обновлении пользователя");
        }
    }
    public JwtUser getCurrentUser() {
        return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
