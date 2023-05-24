package ro.hiringsystem.mapper;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.hiringsystem.model.dto.interview.InterviewConferenceRoomDto;
import ro.hiringsystem.model.entity.interview.InterviewConferenceRoom;

@Mapper(uses = {InterviewParticipantMapper.class}, collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED)
public interface InterviewConferenceRoomMapper {

    @Mapping(target = "participants", source = "participants")
    InterviewConferenceRoomDto toDto(InterviewConferenceRoom interviewConferenceRoom);

    @Mapping(target = "participants", source = "participants", qualifiedBy = InterviewParticipantMapper.ExtraUserInfo.class)
    InterviewConferenceRoomDto toDtoFullyLoaded(InterviewConferenceRoom interviewConferenceRoom);

    @Mapping(target = "participants", source = "participants")
    InterviewConferenceRoom toEntity(InterviewConferenceRoomDto interviewConferenceRoomDto);

}
