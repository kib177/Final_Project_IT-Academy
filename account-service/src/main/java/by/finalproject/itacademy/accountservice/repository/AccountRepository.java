package by.finalproject.itacademy.accountservice.repository;

import by.finalproject.itacademy.accountservice.model.entity.AccountEntity;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    Page<AccountEntity> findByUserUuid(@Param("userUuid") UUID userUuid, Pageable pageable);

    Optional<AccountEntity> findByUuid(@Param("uuid") UUID uuid);

    Optional<AccountEntity> findByUuidAndUserUuid(@Param("uuid") UUID uuid, @Param("userUuid") UUID userUuid);

    List<AccountEntity> findByUserUuidAndUuidIn(@Param("userUuid") UUID userUuid, List<UUID> accountUuids);

    @Query("SELECT a.balance FROM AccountEntity a WHERE a.uuid = :uuid AND a.userUuid = :userUuid")
    Optional<BigDecimal> findBalanceByUuidAndUserUuid(@Param("uuid") UUID uuid, @Param("userUuid") UUID userUuid);


}