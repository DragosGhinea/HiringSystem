package ro.hiringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mapper.*;
import ro.hiringsystem.model.auxiliary.AcademicExperience;
import ro.hiringsystem.model.auxiliary.CV;
import ro.hiringsystem.model.dto.cv.CVDto;
import ro.hiringsystem.model.entity.CandidateUser;
import ro.hiringsystem.model.dto.CandidateUserDto;
import ro.hiringsystem.repository.CandidateUserRepository;
import ro.hiringsystem.service.CandidateUserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CandidateUserServiceImpl implements CandidateUserService {

    private final CandidateUserRepository candidateUserRepository;
    private final CandidateUserMapper candidateUserMapper;

    private final CVMapper cvMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves a candidate user by their ID.
     *
     * @param id the ID of the candidate user
     * @return the candidate user DTO
     * @throws RuntimeException if the user is not found
     */
    @Override
    public CandidateUserDto getById(UUID id) {
        Optional<CandidateUser> candidateUser = candidateUserRepository.findById(id);

        if (candidateUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        return candidateUserMapper.toDto(candidateUser.get());
    }

    /**
     * Retrieves a map of candidate users by their last name.
     *
     * @param lastName the last name of the candidate users
     * @return a map of candidate user IDs to their DTOs
     */
    @Override
    public Map<UUID, CandidateUserDto> getByLastName(String lastName) {
        return listToMap(candidateUserRepository.findByLastName(lastName).stream()
                .map(candidateUserMapper::toDto).toList());
    }

    /**
     * Retrieves a candidate user by their email address.
     *
     * @param email the email address of the candidate user
     * @return the candidate user DTO
     * @throws RuntimeException if the user is not found
     */
    @Override
    public CandidateUserDto getByEmail(String email) {
        Optional<CandidateUser> candidateUser = candidateUserRepository.findByEmail(email);

        if (candidateUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        return candidateUserMapper.toDto(candidateUser.get());
    }

    /**
     * Retrieves a map of all candidate users.
     *
     * @return a map of candidate user IDs to their DTOs
     */
    @Override
    public Map<UUID, CandidateUserDto> getAll() {
        return listToMap(candidateUserRepository.findAll().stream()
                .map(candidateUserMapper::toDto).toList());
    }

    /**
     * Adds multiple candidate users from the given map.
     *
     * @param candidateUserMap a map of candidate user IDs to their DTOs
     */
    @Override
    public void addAllFromGivenMap(Map<UUID, CandidateUserDto> candidateUserMap) {
        candidateUserRepository.saveAll(candidateUserMap.values().stream()
                .map(candidateUserMapper::toEntity).toList());
    }

    /**
     * Adds a candidate user.
     *
     * @param candidateUser the candidate user DTO to add
     */
    @Override
    public void add(CandidateUserDto candidateUser) {
        candidateUserRepository.save(candidateUserMapper.toEntity(candidateUser));
    }

    /**
     * Removes a candidate user by their ID.
     *
     * @param id the ID of the candidate user to remove
     * @throws RuntimeException if the user is not found
     */
    @Override
    public void removeElementById(UUID id) {
        Optional<CandidateUser> candidateUser = candidateUserRepository.findById(id);

        if (candidateUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        } else {
            candidateUserRepository.delete(candidateUser.get());
        }
    }

    /**
     * Saves a candidate user element.
     *
     * @param candidateUserDto the candidate user DTO to save
     */
    @Override
    public void saveElement(CandidateUserDto candidateUserDto) {
        CandidateUser user = candidateUserMapper.toEntity(candidateUserDto);
        candidateUserRepository.save(candidateUserMapper.toEntity(candidateUserDto));
    }

    /**
     * Converts a list of candidate user DTOs to a map of candidate user IDs to their DTOs.
     *
     * @param candidateUserDtoList the list of candidate user DTOs
     * @return a map of candidate user IDs to their DTOs
     */
    @Override
    public Map<UUID, CandidateUserDto> listToMap(List<CandidateUserDto> candidateUserDtoList) {
        Map<UUID, CandidateUserDto> candidateUserDtoMap = new HashMap<>();

        for (CandidateUserDto user : candidateUserDtoList) {
            candidateUserDtoMap.put(user.getId(), user);
        }

        return candidateUserDtoMap;
    }

    /**
     * Creates a new candidate user.
     *
     * @param candidateUserDto the candidate user DTO to create
     * @return the created candidate user DTO
     */
    @Override
    public CandidateUserDto create(CandidateUserDto candidateUserDto) {
        if (candidateUserDto.getId() == null)
            candidateUserDto.setId(UUID.randomUUID());

        candidateUserDto.setPassword(passwordEncoder.encode(candidateUserDto.getPassword()));
        candidateUserDto.setCv(new CV());

        CandidateUser candidateEntity = candidateUserMapper.toEntity(candidateUserDto);
        candidateUserRepository.save(candidateEntity);
        return candidateUserMapper.toDto(candidateEntity);
    }

    /**
     * Retrieves the CV of a candidate user by their ID.
     *
     * @param userId the ID of the candidate user
     * @return the CV DTO of the candidate user
     */
    @Override
    public CVDto getUserCV(UUID userId) {
        return cvMapper.toDto(candidateUserRepository.getUserCV(userId));
    }

    /**
     * Retrieves a paginated list of all candidate users.
     *
     * @param page the page number
     * @param size the number of elements per page
     * @return a list of candidate user DTOs
     */
    @Override
    public List<CandidateUserDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return candidateUserRepository.findAll(pageRequest).stream()
                .map(candidateUserMapper::toDto).toList();
    }

    @Override
    public void updateCv(CVDto cvDto) {
        System.out.println("INTRA");
        CandidateUser candidate = candidateUserRepository.getReferenceById(cvDto.getId());
        CV cv = cvMapper.toEntity(cvDto);
        candidate.setCv(cv);
        candidateUserRepository.save(candidate);
    }
}
