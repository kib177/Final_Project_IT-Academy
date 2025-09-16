package by.finalproject.itacademy.accountschedulerservice.model.dto;


import by.finalproject.itacademy.accountschedulerservice.model.entity.ScheduledOperationEntity;
import by.finalproject.itacademy.common.model.dto.PageDTO;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageOfScheduledOperation extends PageDTO<@Valid ScheduledOperationEntity> {


}

