package by.finalproject.itacademy.classifierservice.model.dto;

import by.finalproject.itacademy.common.model.dto.PageDTO;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageOfCurrency extends PageDTO<CurrencyDTO> {
}
