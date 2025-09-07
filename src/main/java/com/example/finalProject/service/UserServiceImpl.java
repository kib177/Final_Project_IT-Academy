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
    //private final PasswordEncoder passwordEncoder;

    @Transactional
    public boolean create(UserCreate userCreate) {

        userRepository.save(
                UserEntity.builder()
                        .mail(userCreate.getMail())
                        .fio(userCreate.getFio())
                        .role(userCreate.getRole())
                        .status(userCreate.getStatus())
                        .password(userCreate.getPassword())
                        .build()
        );
        return true;
    }

    @Override
    public Optional<User> getById(UUID uuid) {
        UserEntity userEntity = userRepository.getByUuid(uuid)
                .orElseThrow(() -> new CabinetException("User not found" + uuid));
        return Optional.ofNullable(User.builder()
                .uuid(userEntity.getUuid())
                .dt_create(userEntity.getDt_create())
                .dt_update(userEntity.getDt_update())
                .mail(userEntity.getMail())
                .fio(userEntity.getFio())
                .role(userEntity.getRole())
                .status(userEntity.getStatus())
                .build());
    }

    @Override
    public PageOfUser<Object> getUsersPage(Pageable pageable) {
        Page<UserEntity> entityPage = userRepository.findAll(pageable);
        List<Object> content = new ArrayList<>();
        for (UserEntity entity : entityPage.getContent()) {
            content.add(User.builder()
                    .uuid(entity.getUuid())
                    .dt_create(entity.getDt_create())
                    .dt_update(entity.getDt_update())
                    .mail(entity.getMail())
                    .fio(entity.getFio())
                    .role(entity.getRole())
                    .status(entity.getStatus())
                    .build());
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
