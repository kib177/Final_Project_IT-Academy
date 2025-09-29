package by.finalproject.itacademy.userservice.repository;


import by.finalproject.itacademy.userservice.model.dto.User;
import by.finalproject.itacademy.userservice.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    UserEntity findByMail(String mail);
    boolean existsByMail(String mail);
    Optional<UserEntity> getByUuid(UUID uuid);
}
