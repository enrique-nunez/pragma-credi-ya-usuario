package co.com.pragma.api.mapper;

import co.com.pragma.api.dto.UserCreateRequestDto;
import co.com.pragma.api.dto.UserResponseDto;
import co.com.pragma.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toUser(UserCreateRequestDto userCreateRequestDto);

    @Mapping(source = "creationDate", target = "creationDate")
    UserResponseDto toUserResponseDto(User user);
}
