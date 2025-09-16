package by.finalproject.itacademy.accountschedulerservice.model.dto;


import by.finalproject.itacademy.accountschedulerservice.model.entity.ScheduledOperation;
import by.finalproject.itacademy.common.model.dto.PageDTO;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageOfScheduledOperation extends PageDTO<@Valid ScheduledOperation> {


}

