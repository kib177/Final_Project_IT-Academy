package com.example.finalProject.storage.repository;

import com.example.finalProject.storage.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByMail(String mail);
    boolean existsByMail(String mail);

    Optional<UserEntity> findByUuid(UUID uuid);
}
