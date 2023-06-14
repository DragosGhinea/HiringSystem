package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import ro.hiringsystem.model.dto.ManagerUserDto;
import ro.hiringsystem.model.entity.ManagerUser;

@Mapper
public interface ManagerUserMapper {
    ManagerUserDto toDto(ManagerUser managerUser);

    ManagerUser toEntity(ManagerUserDto userDto);

}
