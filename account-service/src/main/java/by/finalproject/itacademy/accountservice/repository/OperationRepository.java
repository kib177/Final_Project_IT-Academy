package by.finalproject.itacademy.accountservice.repository;


import by.finalproject.itacademy.accountservice.model.entity.OperationEntity;
import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, UUID> {
    Page<OperationEntity> findByAccount(@Param("account") UUID account, Pageable pageable);
    Optional<OperationEntity> findByUuidAndAccount(@Param("uuid") UUID uuid, @Param("account") UUID account);
}