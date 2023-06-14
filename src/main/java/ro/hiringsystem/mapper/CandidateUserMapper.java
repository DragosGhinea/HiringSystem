package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.hiringsystem.model.dto.CandidateUserDto;
import ro.hiringsystem.model.entity.CandidateUser;

@Mapper
public interface CandidateUserMapper {
    @Mapping(target = "jobApplications", ignore = true)
    CandidateUserDto toDto(final CandidateUser candidateUser);

    @Mapping(target = "jobApplications", ignore = true)
    CandidateUser toEntity(final CandidateUserDto userDto);

}
