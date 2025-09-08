
@Mapper(componentModel = "spring")
public interface BaseUserMapper {

    UserEntity fromUserEntity(UserCreate user);
    User toDto(UserEntity entity);
    UserEntity toEntity(User dto);
    UserEntity fromRegistrationDto(UserRegistration dto);
    UserEntity fromCreateDto(UserCreate dto);
}
