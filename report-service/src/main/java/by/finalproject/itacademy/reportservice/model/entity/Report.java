package by.finalproject.itacademy.reportservice.model.entity;


import by.finalproject.itacademy.common.model.entity.BaseEntity;
import by.finalproject.itacademy.reportservice.model.enums.ReportStatusEnum;
import by.finalproject.itacademy.reportservice.model.enums.ReportTypeEnum;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "report")
public class Report {

    @EmbeddedId
    private BaseEntity baseEntity;

    private ReportStatusEnum status;

    private ReportTypeEnum type;

    private String description;

    private Object params; // Может быть ReportParamBalance, ReportParamByDate или ReportParamByCategory
}
