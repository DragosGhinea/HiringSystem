package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ro.hiringsystem.model.entity.InterviewerUser;
import ro.hiringsystem.model.dto.InterviewerUserDto;

@Mapper
public interface InterviewerUserMapper {

    InterviewerUserMapper INSTANCE = Mappers.getMapper(InterviewerUserMapper.class);

    InterviewerUserDto toDto(InterviewerUser interviewerUser);

    InterviewerUser toEntity(InterviewerUserDto userDto);

}
