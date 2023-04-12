package ro.hiringsystem.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.dto.UserDto;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
