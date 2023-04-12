package ro.hiringsystem.service;

import ro.hiringsystem.model.CandidateUser;
import ro.hiringsystem.model.dto.CandidateUserDto;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface CandidateUserService extends UserService<CandidateUserDto> {

    Map<UUID, CandidateUserDto> getByLastName(String lastName);

}
