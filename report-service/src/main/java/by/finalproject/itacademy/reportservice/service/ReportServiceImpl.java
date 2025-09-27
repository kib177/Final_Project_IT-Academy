package by.finalproject.itacademy.reportservice.service;


import by.finalproject.itacademy.reportservice.model.dto.PageOfReport;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@AllArgsConstructor
public class ReportServiceImpl {
    private final ReportRepository reportRepository;


    @Override
    public Report getById(UUID id) {
        ReportEntity entity = reportRepository.getById(id);
        ReportParams params = null;
        if (entity.getReportType() == ReportType.BALANCE) {
            params = ReportParamBalance.builder()
                    .accounts(entity.getAccounts())
                    .build();
        }
        else if (entity.getReportType() == ReportType.BY_CATEGORY) {
            params = ReportParamByCategory.builder()
                    .accounts(entity.getAccounts())
                    .categories(entity.getCategories())
                    .from(entity.getDateFrom())
                    .to(entity.getDateTo())
                    .build();
        }
        else if (entity.getReportType() == ReportType.BY_DATE) {
            params = ReportParamByCategory.builder()
                    .accounts(entity.getAccounts())
                    .categories(entity.getCategories())
                    .from(entity.getDateFrom())
                    .to(entity.getDateTo())
                    .build();
        }
        return Report.builder()
                .id(entity.getUuid())
                .dtCreate(entity.getDtCreate())
                .dtUpdate(entity.getDtUpdate())
                .status(entity.getStatus())
                .type(entity.getReportType())
                .description(entity.getDescription())
                .params(params)
                .build();
    }

    @Override
    public void save(Report report) {

        ReportEntity.ReportEntityBuilder builder = ReportEntity.builder()
                .uuid(report.getId())
                .dtCreate(report.getDtCreate())
                .dtUpdate(report.getDtUpdate())
                .status(report.getStatus())
                .reportType(report.getType());

        if (report.getType() == ReportType.BALANCE) {
            ReportParamBalance params = (ReportParamBalance) report.getParams();
            builder.accounts(params.getAccounts());

        }
        else if (report.getType() == ReportType.BY_CATEGORY) {
            ReportParamByCategory params = (ReportParamByCategory) report.getParams();
            builder.accounts(params.getAccounts());
            builder.categories(params.getCategories());
            builder.dateFrom(params.getFrom());
            builder.dateTo(params.getTo());
        }
        else if (report.getType() == ReportType.BY_DATE) {
            ReportParamByDate params = (ReportParamByDate) report.getParams();
            builder.accounts(params.getAccounts());
            builder.categories(params.getCategories());
            builder.dateFrom(params.getFrom());
            builder.dateTo(params.getTo());
        }
        reportRepository.save(builder.build());

    }

    @Override
    public by.it_academy.jd2.dto.Page getPage(int page, int size) {
        org.springframework.data.domain.Page<ReportEntity> entityPage =
                reportRepository.findAll(PageRequest.of(page, size));

        List<Report> reports = new ArrayList<>();

        for (ReportEntity entity : entityPage.getContent()) {
            ReportParams params = null;

            if (entity.getReportType() == ReportType.BALANCE) {
                params = ReportParamBalance.builder()
                        .accounts(entity.getAccounts())
                        .build();
            }
            else if (entity.getReportType() == ReportType.BY_CATEGORY) {
                params = ReportParamByCategory.builder()
                        .accounts(entity.getAccounts())
                        .categories(entity.getCategories())
                        .from(entity.getDateFrom())
                        .to(entity.getDateTo())
                        .build();
            }
            else if (entity.getReportType() == ReportType.BY_DATE) {
                params = ReportParamByDate.builder()
                        .accounts(entity.getAccounts())
                        .from(entity.getDateFrom())
                        .to(entity.getDateTo())
                        .build();
            }

            Report report = Report.builder()
                    .id(entity.getUuid())
                    .dtCreate(entity.getDtCreate())
                    .dtUpdate(entity.getDtUpdate())
                    .status(entity.getStatus())
                    .type(entity.getReportType())
                    .description(entity.getDescription())
                    .params(params)
                    .build();

            reports.add(report);
        }

        return PageOfReport.builder()
                .number(entityPage.getNumber())
                .size(entityPage.getSize())
                .totalPages(entityPage.getTotalPages())
                .totalElements(entityPage.getTotalElements())
                .first(entityPage.isFirst())
                .numberOfElements(entityPage.getNumberOfElements())
                .last(entityPage.isLast())
                .content(reports)
                .build();
    }
}
