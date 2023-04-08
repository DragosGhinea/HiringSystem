package ro.hiringsystem.service;

import ro.hiringsystem.model.CandidateUser;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface CandidateUserService extends UserService<CandidateUser> {

    Map<UUID, CandidateUser> getByLastName(String lastName);

}
