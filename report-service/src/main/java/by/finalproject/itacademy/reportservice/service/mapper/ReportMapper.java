package by.finalproject.itacademy.reportservice.service.mapper;

import by.finalproject.itacademy.reportservice.model.dto.ReportResponse;
import by.finalproject.itacademy.reportservice.model.entity.ReportEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReportMapper {
    ReportResponse toDto(ReportEntity reportEntity);
}
