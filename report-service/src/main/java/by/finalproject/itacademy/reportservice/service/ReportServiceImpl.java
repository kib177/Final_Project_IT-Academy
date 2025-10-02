package by.finalproject.itacademy.reportservice.service;

import by.finalproject.itacademy.reportservice.model.dto.*;
import by.finalproject.itacademy.reportservice.model.entity.ReportEntity;
import by.finalproject.itacademy.reportservice.model.enums.ReportStatusEnum;
import by.finalproject.itacademy.reportservice.model.enums.ReportTypeEnum;
import by.finalproject.itacademy.reportservice.repository.ReportRepository;
import by.finalproject.itacademy.reportservice.service.api.IReportService;
import by.finalproject.itacademy.reportservice.service.mapper.PageMapper;
import by.finalproject.itacademy.reportservice.service.mapper.ReportMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ReportServiceImpl implements IReportService {
    private final ReportRepository reportRepository;
    private final ReportMapper reportMapper;
    private final PageMapper pageMapper;


    @Override
    public ReportResponse getById(UUID id) {
        ReportEntity entity = reportRepository.getById(id);
       return reportMapper.toDto(entity);
    }

    @Transactional
    @Override
    public void save(ReportRequest report, String type) {

        ReportEntity reportEntity = ReportEntity.builder()
                .dtCreate(LocalDateTime.now())
                .dtUpdate(LocalDateTime.now())
                .status(ReportStatusEnum.DONE)
                .type(ReportTypeEnum.valueOf(type))
                .params(report)
                .build();

        reportRepository.save(reportEntity);

    }

    @Override
    public PageOfReport getPage(Pageable pageable) {
        Page<ReportEntity> entityPage =
                reportRepository.findAll(pageable);
        return pageMapper.toPageOfUser(entityPage, reportMapper);
    }
}
