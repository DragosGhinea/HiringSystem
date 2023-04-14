package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.hiringsystem.model.ManagerUser;
import ro.hiringsystem.model.dto.ManagerUserDto;

@Mapper
public interface ManagerUserMapper {

    ManagerUserMapper INSTANCE = Mappers.getMapper(ManagerUserMapper.class);

    ManagerUserDto toDto(ManagerUser managerUser);

    ManagerUser toEntity(ManagerUserDto userDto);

}
