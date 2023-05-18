package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import ro.hiringsystem.model.dto.CandidateUserDto;
import ro.hiringsystem.model.entity.CandidateUser;

@Mapper
public interface CandidateUserMapper {
    CandidateUserDto toDto(final CandidateUser candidateUser);

    CandidateUser toEntity(final CandidateUserDto userDto);

}
