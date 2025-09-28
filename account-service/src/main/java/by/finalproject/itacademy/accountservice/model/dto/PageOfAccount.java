package by.finalproject.itacademy.accountservice.model.dto;

import by.finalproject.itacademy.common.model.dto.PageDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
@AllArgsConstructor
public class PageOfAccount extends PageDTO<AccountResponse> {
}
