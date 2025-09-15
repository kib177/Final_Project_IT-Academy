package by.finalproject.itacademy.accountservice.repository;


import by.finalproject.itacademy.accountservice.model.entity.OperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<OperationEntity, UUID> {
    Page<OperationEntity> findByAccountUuid(UUID accountUuid, Pageable pageable);

    @Query("SELECT o FROM OperationEntity o WHERE o.uuid = :accountUuid AND o.date BETWEEN :startDate AND :endDate")
    Page<OperationEntity> findByAccountUuidAndDateRange(@Param("accountUuid") UUID accountUuid,
                                                        @Param("startDate") Long startDate,
                                                        @Param("endDate") Long endDate,
                                                        Pageable pageable);
}