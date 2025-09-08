package com.example.finalProject.service;

import com.example.finalProject.dto.PageOfUser;
import com.example.finalProject.dto.User;
import com.example.finalProject.dto.UserCreate;
import com.example.finalProject.service.api.IUserService;
import com.example.finalProject.service.api.exception.CabinetException;
import com.example.finalProject.storage.entity.UserEntity;
import com.example.finalProject.storage.repository.UserRepository;
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
    private final BaseUserMapper baseUserMapper;
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean create(UserCreate userCreate) {

        UserEntity userEntity = baseUserMapper.fromCreateDto(userCreate);
        userRepository.save(userEntity);
        return true;
    }

    @Override
    public Optional<User> getById(UUID uuid) {
        UserEntity userEntity = userRepository.getByUuid(uuid)
                .orElseThrow(() -> new CabinetException("User not found" + uuid));
        return Optional.ofNullable(baseUserMapper.toDto(userEntity));
    }

    @Override
    public PageOfUser<Object> getUsersPage(Pageable pageable) {
        Page<UserEntity> entityPage = userRepository.findAll(pageable);
        List<Object> content = entityPage.getContent();
                .stream()
                .map(baseUserMapper::toDto)
                .collect(Collectors.toList());
        }
        return PageOfUser.builder()
                .number(entityPage.getNumber())
                .size(entityPage.getSize())
                .totalPages(entityPage.getTotalPages())
                .totalElements(entityPage.getTotalElements())
                .first(entityPage.isFirst())
                .numberOfElements(entityPage.getNumberOfElements())
                .last(entityPage.isLast())
                .content(content)
                .build();
    }
}
