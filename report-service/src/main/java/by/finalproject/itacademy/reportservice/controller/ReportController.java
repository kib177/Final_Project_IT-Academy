package by.finalproject.itacademy.reportservice.controller;


import by.finalproject.itacademy.reportservice.model.dto.PageOfReport;
import by.finalproject.itacademy.reportservice.model.enums.ReportTypeEnum;
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

    @PostMapping("/{type}")
    public String addNewReport(@Valid @RequestBody ReportTypeEnum reportType) {

    }

    @GetMapping()
    public ResponseEntity<PageOfReport> createPageOfReport(Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body();
    }

    @GetMapping("/{uuid}/export")
    public PageOfReport getPageOfReport(@PathVariable UUID uuid) {

    }

    @RequestMapping(path = "/{uuid}/export", method = RequestMethod.HEAD)
    public PageOfReport getPageOfReport(@PathVariable UUID uuid) {

    }


}
