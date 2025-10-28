package by.finalproject.itacademy.reportservice.service.api;

import by.finalproject.itacademy.reportservice.model.dto.PageOfReport;
import by.finalproject.itacademy.reportservice.model.dto.ReportRequest;
import by.finalproject.itacademy.reportservice.model.dto.ReportResponse;
import by.finalproject.itacademy.reportservice.model.enums.ReportTypeEnum;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IReportService {
    ReportResponse getById(UUID id);

    void save(ReportRequest report, ReportTypeEnum type);

    PageOfReport getPage(Pageable pageable);

    void checkReport(UUID uuid);
}
