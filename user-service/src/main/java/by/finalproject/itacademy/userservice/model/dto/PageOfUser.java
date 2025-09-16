package by.finalproject.itacademy.userservice.model.dto;

import by.finalproject.itacademy.common.model.dto.PageDTO;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class PageOfUser extends PageDTO<User> {
}
