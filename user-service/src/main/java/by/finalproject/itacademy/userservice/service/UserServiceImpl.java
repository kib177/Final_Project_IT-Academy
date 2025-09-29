package by.finalproject.itacademy.userservice.service;

import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import by.finalproject.itacademy.common.exception.EntityAlreadyExistsException;
import by.finalproject.itacademy.common.exception.InvalidCredentialsException;
import by.finalproject.itacademy.common.exception.UserNotFoundException;
import by.finalproject.itacademy.common.jwt.JwtUser;
import by.finalproject.itacademy.userservice.feign.AuditServiceClient;
import by.finalproject.itacademy.userservice.model.dto.AuditEventRequest;
import by.finalproject.itacademy.userservice.model.dto.PageOfUser;
import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.dto.UserCreate;
import by.finalproject.itacademy.userservice.service.api.IUserService;
import by.finalproject.itacademy.userservice.service.api.IVerificationCodeService;
import by.finalproject.itacademy.userservice.model.entity.UserEntity;
import by.finalproject.itacademy.userservice.service.mapper.PageMapper;
import by.finalproject.itacademy.userservice.service.mapper.UserMapper;
import by.finalproject.itacademy.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
    private final PasswordEncoder passwordEncoder;
    private final ValidService validService;
    private final AuditServiceClient auditServiceClient;

    @Transactional
    @Override
    public void create(UserCreate userCreate) {
        log.info("Creating new user with email: {}", userCreate.getMail());

        if (userRepository.existsByMail(userCreate.getMail())) {
            throw new EntityAlreadyExistsException("Некорректные данные");
        }

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
                        .userInfo("Создан пользователь")
                        .essenceId(userEntity.getUuid())
                        .type(EssenceTypeEnum.USER)
                        .build());
    }

    @Override
    public User getById(UUID uuid) {
        log.info("Getting user by UUID: {}", uuid);
        UserEntity userEntity = userRepository.getByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));;
        return userMapper.toDto(userEntity);
    }

    @Override
    public PageOfUser getUsersPage(Pageable pageable) {
        log.info("Getting users page: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        Page<UserEntity> entityPage = userRepository.findAll(pageable);
        return pageMapper.toPageOfUser(entityPage, userMapper);
    }


    @Transactional
    @Override
    public void updateUser(UUID uuid, Long dtUpdate, UserCreate userCreate) {
        log.info("Updating user with UUID: {}", uuid);

        if (!validService.isValidUuid(String.valueOf(uuid))) {
            throw new InvalidCredentialsException("Некорректный формат UUID: " + uuid);
        }

        UserEntity userEntity = userRepository.getByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));;

        if (!userEntity.getDtUpdate().equals(dtUpdate)) {
            throw new InvalidCredentialsException("Конфликт версий данных. Получите актуальную версию пользователя");
        }

        userEntity.setFio(userCreate.getFio());
        userEntity.setMail(userCreate.getMail());

        if (passwordEncoder.matches(userCreate.getPassword(), userEntity.getPassword())) {
            throw new InvalidCredentialsException("Wrong credentials");
        }

        userRepository.save(userEntity);
            auditServiceClient.logEvent(
                    AuditEventRequest.builder()
                            .jwtUser(getCurrentUser())
                            .userInfo("Изменен пользователь")
                            .essenceId(userEntity.getUuid())
                            .type(EssenceTypeEnum.USER)
                            .build());
    }

    public JwtUser getCurrentUser() {
        return (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
