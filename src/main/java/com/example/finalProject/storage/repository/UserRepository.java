package com.example.finalProject.storage.repository;

import com.example.finalProject.storage.entity.UserEntity;
import org.mapstruct.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Mapper
@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByMail(String mail);
    boolean existsByMail(String mail);
    Optional<UserEntity> getByUuid(UUID uuid);

    /* Для выборки метода findAll по ролям, чтобы получать более точную статистику
    @EntityGraph(attributePaths = {"role"})
    @Query("SELECT u FROM UserEntity u WHERE u.role = 'USER'")
    PageOfUser<UserEntity> findAll(Pageable pageable)
    */
}
