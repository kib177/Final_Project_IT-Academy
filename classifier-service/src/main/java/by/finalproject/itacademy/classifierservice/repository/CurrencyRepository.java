package by.finalproject.itacademy.classifierservice.repository;

import by.finalproject.itacademy.classifierservice.model.entity.CurrencyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, UUID> {
    Page<CurrencyEntity> findAll(Pageable pageable);
}
