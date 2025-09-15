package by.finalproject.itacademy.userservice.service;

import by.finalproject.itacademy.common.dto.PageDTO;
import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.dto.UserCreate;
import by.finalproject.itacademy.userservice.service.api.IUserService;
import by.finalproject.itacademy.userservice.service.api.exception.CabinetException;
import by.finalproject.itacademy.userservice.model.entity.UserEntity;
import by.finalproject.itacademy.userservice.model.entity.VerificationEntity;
import by.finalproject.itacademy.userservice.service.mapper.PageMapper;
import by.finalproject.itacademy.userservice.service.mapper.UserMapper;
import by.finalproject.itacademy.userservice.repository.UserRepository;
import by.finalproject.itacademy.userservice.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public PageDTO<Object> getUsersPage(Pageable pageable) {
        Page<UserEntity> entityPage = userRepository.findAll(pageable);
        return pageMapper.toPageOfUser(entityPage, userMapper);
    }
}
