package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import ro.hiringsystem.model.dto.InterviewerUserDto;
import ro.hiringsystem.model.entity.InterviewerUser;

@Mapper
public interface InterviewerUserMapper {

    InterviewerUserDto toDto(InterviewerUser interviewerUser);

    InterviewerUser toEntity(InterviewerUserDto userDto);

}
