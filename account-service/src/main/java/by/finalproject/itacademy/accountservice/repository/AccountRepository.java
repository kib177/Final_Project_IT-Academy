package by.finalproject.itacademy.accountservice.repository;

import by.finalproject.itacademy.accountservice.model.entity.AccountEntity;
import by.finalproject.itacademy.accountservice.model.enums.AccountTypeEnum;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<AccountEntity, UUID> {
    Page<AccountEntity> findByUserUuid(UUID userUuid, Pageable pageable);

    @Query("SELECT a FROM AccountEntity a WHERE a.uuid = :userUuid AND a.type = :type")
    Page<AccountEntity> findByUserUuidAndType(@Param("userUuid") UUID userUuid,
                                              @Param("type") AccountTypeEnum type,
                                              Pageable pageable);

    @Modifying
    @Query("UPDATE AccountEntity a SET a.balance = a.balance + :amount WHERE a.uuid = :uuid")
    void updateBalance(@Param("uuid") UUID uuid, @Param("amount") Double amount);
}