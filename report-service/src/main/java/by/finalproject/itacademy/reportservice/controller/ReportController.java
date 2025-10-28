package by.finalproject.itacademy.reportservice.controller;

import by.finalproject.itacademy.reportservice.model.dto.PageOfReport;
import by.finalproject.itacademy.reportservice.model.dto.ReportRequest;
import by.finalproject.itacademy.reportservice.model.dto.ReportResponse;
import by.finalproject.itacademy.reportservice.model.enums.ReportTypeEnum;
import by.finalproject.itacademy.reportservice.service.api.IReportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/report")
@RequiredArgsConstructor
public class ReportController {
    private final IReportService reportService;

    @PostMapping("/{type}")
    public ResponseEntity<?> addNewReport(@Valid @PathVariable ReportTypeEnum type,
                                          @Valid @RequestBody ReportRequest reportRequest) {
        reportService.save(reportRequest, type);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping()
    public ResponseEntity<PageOfReport> createPageOfReport(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(reportService.getPage(pageable));
    }

    @GetMapping("/{uuid}/export")
    public ReportResponse getPageOfReport(@PathVariable UUID uuid) {
        return ResponseEntity.status(HttpStatus.OK).body(reportService.getById(uuid)).getBody();
    }

    @RequestMapping(path = "/{uuid}/export", method = RequestMethod.HEAD)
    public ResponseEntity<?> checkReport(@PathVariable UUID uuid) {
        reportService.checkReport(uuid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
