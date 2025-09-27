package by.finalproject.itacademy.accountservice.model.dto;

import by.finalproject.itacademy.common.model.dto.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class PageOfAccount extends PageDTO<AccountDTO> {
}
