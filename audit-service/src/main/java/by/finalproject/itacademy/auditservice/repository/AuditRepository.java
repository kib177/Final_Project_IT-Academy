package by.finalproject.itacademy.auditservice.repository;

import by.finalproject.itacademy.auditservice.model.EssenceTypeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuditRepository extends JpaRepository<AuditEntity, UUID> {
    Page<AuditEntity> findByType(EssenceTypeEnum type, Pageable pageable);

    @Query("SELECT a FROM AuditEntity a WHERE a.userUuid = :userUuid")
    Page<AuditEntity> findByUserUuid(@Param("userUuid") UUID userUuid, Pageable pageable);

    @Query("SELECT a FROM AuditEntity a WHERE a.type = :type AND a.id = :id")
    Page<AuditEntity> findByTypeAndId(@Param("type") EssenceTypeEnum type,
                                      @Param("id") String id,
                                      Pageable pageable);
}
