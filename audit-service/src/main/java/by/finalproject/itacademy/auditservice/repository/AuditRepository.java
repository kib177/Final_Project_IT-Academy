package by.finalproject.itacademy.auditservice.repository;

import by.finalproject.itacademy.auditservice.model.entity.AuditEntity;
import by.finalproject.itacademy.auditservice.model.enums.EssenceTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntity, UUID> {
    Page<AuditEntity> findByType(EssenceTypeEnum type, Pageable pageable);
    Page<AuditEntity> findByUserUuid(UUID userUuid, Pageable pageable);
    Page<AuditEntity> findByTypeAndId(EssenceTypeEnum type, String id, Pageable pageable);
}
