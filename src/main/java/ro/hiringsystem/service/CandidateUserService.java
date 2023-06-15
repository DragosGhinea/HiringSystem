package ro.hiringsystem.service;

import ro.hiringsystem.model.dto.CandidateUserDto;
import ro.hiringsystem.model.dto.cv.CVDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CandidateUserService extends UserService<CandidateUserDto> {

    Map<UUID, CandidateUserDto> getByLastName(String lastName);

    CVDto getUserCV(UUID userId);

    List<CandidateUserDto> getAll(int page, int size);

    void updateCv(CVDto cvDto);
}
