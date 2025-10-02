package by.finalproject.itacademy.reportservice.model.dto;

import by.finalproject.itacademy.common.model.dto.PageDTO;
import by.finalproject.itacademy.reportservice.model.entity.ReportEntity;
import by.finalproject.itacademy.reportservice.repository.ReportRepository;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageOfReport extends PageDTO<ReportResponse> {
}
