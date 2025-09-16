package by.finalproject.itacademy.classifierservice.repository;

import by.finalproject.itacademy.classifierservice.model.entity.OperationCategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.UUID;

public interface OperationCategoryRepository extends JpaRepository<OperationCategoryEntity, UUID> {
    Page<OperationCategoryEntity> findAll(Pageable pageable);
}
