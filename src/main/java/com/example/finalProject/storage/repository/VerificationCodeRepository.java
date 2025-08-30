package com.example.finalProject.storage.repository;

import com.example.finalProject.storage.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {
    Optional<VerificationCode> findByMailAndCode(String mail, String code);
    void deleteByMail(String mail);
}
