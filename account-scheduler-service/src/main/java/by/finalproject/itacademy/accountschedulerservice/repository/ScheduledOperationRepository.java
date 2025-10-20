package by.finalproject.itacademy.accountschedulerservice.repository;

import by.finalproject.itacademy.accountschedulerservice.model.entity.ScheduledOperationEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ScheduledOperationRepository extends JpaRepository<ScheduledOperationEntity, UUID> {

    @Query("SELECT so FROM ScheduledOperationEntity so WHERE so.operation.account IN :accountIds")
    Page<ScheduledOperationEntity> findByAccountIds(@Param("accountIds") List<UUID> accountIds, Pageable pageable);

    Page<ScheduledOperationEntity> findAllByUuidIn(Pageable pageable, List<UUID> uuids);
}