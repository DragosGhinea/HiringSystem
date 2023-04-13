package ro.hiringsystem.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.model.dto.CandidateUserDto;

@Mapper
public interface CandidateUserMapper {

    CandidateUserMapper INSTANCE = Mappers.getMapper(CandidateUserMapper.class);

    CandidateUserDto toDto(CandidateUser candidateUser);

    CandidateUser toEntity(CandidateUserDto userDto);

}
