package by.finalproject.itacademy.reportservice.repository;

import by.finalproject.itacademy.reportservice.model.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReportRepository extends JpaRepository<ReportEntity, UUID> {
}
