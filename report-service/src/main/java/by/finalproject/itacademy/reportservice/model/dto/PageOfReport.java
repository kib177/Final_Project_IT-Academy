package by.finalproject.itacademy.reportservice.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageOfReport {
    private int number;
    private int size;
    @JsonProperty("total_pages")
    private int totalPages;
    @JsonProperty("total_elements")
    private long totalElements;
    private boolean first;
    @JsonProperty("number_of_elements")
    private int numberOfElements;
    private boolean last;
    private List<ReportResponse> content;
}
