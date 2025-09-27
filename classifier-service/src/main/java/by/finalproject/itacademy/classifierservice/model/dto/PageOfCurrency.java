package by.finalproject.itacademy.classifierservice.model.dto;

import by.finalproject.itacademy.common.model.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageOfCurrency extends PageDTO<CurrencyResponse> {

}
