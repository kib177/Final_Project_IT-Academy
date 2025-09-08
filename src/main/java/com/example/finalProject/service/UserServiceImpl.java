package com.example.finalProject.service;

import com.example.finalProject.dto.PageOfUser;
import com.example.finalProject.dto.User;
import com.example.finalProject.dto.UserCreate;
import com.example.finalProject.service.api.IUserService;
import com.example.finalProject.service.api.exception.CabinetException;
import com.example.finalProject.storage.entity.VerificationEntity;
import com.example.finalProject.storage.mapper.PageMapper;
import com.example.finalProject.storage.mapper.UserMapper;
import com.example.finalProject.storage.entity.UserEntity;
import com.example.finalProject.storage.repository.UserRepository;
import com.example.finalProject.storage.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PageMapper pageMapper;
    private final VerificationCodeRepository verificationCodeRepository;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean create(UserCreate userCreate) {

        UserEntity userEntity = userMapper.fromCreateDto(userCreate);
        userRepository.save(userEntity);

        String code = UUID.randomUUID().toString().substring(0, 6);
        VerificationEntity verifyCode = VerificationEntity.builder()
                .mail(userCreate.getMail())
                .code(code)
                .build();
        verificationCodeRepository.save(verifyCode);
        return true;
    }

    @Override
    public Optional<User> getById(UUID uuid) {
        UserEntity userEntity = userRepository.getByUuid(uuid)
                .orElseThrow(() -> new CabinetException("User not found" + uuid));
        return Optional.ofNullable(userMapper.toDto(userEntity));
    }

    @Override
    public PageOfUser<Object> getUsersPage(Pageable pageable) {
        Page<UserEntity> entityPage = userRepository.findAll(pageable);
        return pageMapper.toPageOfUser(entityPage, userMapper);
    }
}
