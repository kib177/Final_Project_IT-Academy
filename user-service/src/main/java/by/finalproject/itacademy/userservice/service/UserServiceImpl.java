package by.finalproject.itacademy.userservice.service;

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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static java.time.Instant.now;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {
    private final IVerificationCodeService verificationCodeService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PageMapper pageMapper;
    private final VerificationCodeRepository verificationCodeRepository;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean create(UserCreate userCreate) {

        UserEntity userEntity = userMapper.fromCreateDto(userCreate);
        if(userEntity != null) {
            userEntity.setDtCreate(LocalDateTime.now());
            userEntity.setDtUpdate(
                    userEntity.getDtCreate());
            userRepository.save(userEntity);
        }
        verificationCodeService.generateCode(userCreate.getMail());
        return true;
    }

    @Override
    public Optional<User> getById(UUID uuid) {
        UserEntity userEntity = userRepository.getByUuid(uuid)
                .orElseThrow(() -> new CabinetException("User not found" + uuid));
        return Optional.ofNullable(userMapper.toDto(userEntity));
    }

    @Override
    public PageOfUser getUsersPage(Pageable pageable) {
        Page<UserEntity> entityPage = userRepository.findAll(pageable);
        return pageMapper.toPageOfUser(entityPage, userMapper);
    }
}
