package com.example.finalProject.storage.repository;

import com.example.finalProject.storage.entity.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface    VerificationCodeRepository extends JpaRepository<VerificationEntity, UUID> {
    Optional<VerificationEntity> findByMailAndCode(String mail, String code);
    void deleteByMail(String mail);
}
