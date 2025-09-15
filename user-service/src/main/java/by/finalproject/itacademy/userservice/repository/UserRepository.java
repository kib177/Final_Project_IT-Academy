package by.finalproject.itacademy.userservice.repository;


import by.finalproject.itacademy.userservice.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

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
