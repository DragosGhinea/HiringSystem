package ro.hiringsystem.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Qualifier;
import ro.hiringsystem.model.dto.interview.InterviewParticipantDto;
import ro.hiringsystem.model.dto.interview.InterviewParticipantExtraUserInfoDto;
import ro.hiringsystem.model.entity.interview.InterviewParticipant;

import java.util.List;

@Mapper
public interface InterviewParticipantMapper {
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "interviewRoom", ignore = true)
    InterviewParticipant toEntity(InterviewParticipantDto dto);


    InterviewParticipantDto toDto(InterviewParticipant entity);

    @Qualifier
    @interface ExtraUserInfo {}

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "primaryEmail", source = "user.primaryEmail")
    @Mapping(target = "mailList", source = "user.mailList")
    @Mapping(target = "phoneNumberList", source = "user.phoneNumberList")
    @Mapping(target = "birthDate", source = "user.birthDate")
    @ExtraUserInfo
    InterviewParticipantExtraUserInfoDto toDtoExtraUserInfo(InterviewParticipant entity);

    List<InterviewParticipant> toEntityList(List<InterviewParticipantDto> dtoList);

    List<InterviewParticipantDto> toDtoList(List<InterviewParticipant> entityList);

    @IterableMapping(qualifiedBy = ExtraUserInfo.class)
    List<InterviewParticipantExtraUserInfoDto> toDtoExtraUserInfoList(List<InterviewParticipant> entityList);
}