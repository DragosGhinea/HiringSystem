package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import ro.hiringsystem.model.dto.InterviewParticipantDto;
import ro.hiringsystem.model.entity.interview.InterviewParticipant;

import java.util.List;

@Mapper
public interface InterviewParticipantMapper {
    InterviewParticipant toEntity(InterviewParticipantDto dto);

    InterviewParticipantDto toDto(InterviewParticipant entity);

    List<InterviewParticipant> toEntityList(List<InterviewParticipantDto> dtoList);

    List<InterviewParticipantDto> toDtoList(List<InterviewParticipant> entityList);
}