package by.finalproject.itacademy.mailservice.repository;

import by.finalproject.itacademy.mailservice.model.entity.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationEntity, UUID> {

    Optional<VerificationEntity> findByMailAndCode(String mail, String code);
    void deleteByMail(String mail);
    boolean existsByMailAndCode(String mail, String code);
}
