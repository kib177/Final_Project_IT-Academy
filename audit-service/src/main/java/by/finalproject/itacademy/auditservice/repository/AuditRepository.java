package by.finalproject.itacademy.auditservice.repository;

import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntity, UUID> {
    Page<AuditEntity> findAll (Pageable pageable);
    Optional<AuditEntity> findAuditEntityByUuid(UUID uuid);
}
