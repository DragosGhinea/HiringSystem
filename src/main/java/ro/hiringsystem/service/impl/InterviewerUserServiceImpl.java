package ro.hiringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mapper.InterviewerUserMapper;
import ro.hiringsystem.model.dto.InterviewerUserDto;
import ro.hiringsystem.model.entity.InterviewerUser;
import ro.hiringsystem.model.enums.InterviewerType;
import ro.hiringsystem.repository.InterviewerUserRepository;
import ro.hiringsystem.service.InterviewerUserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class InterviewerUserServiceImpl implements InterviewerUserService {

    private final InterviewerUserRepository interviewerUserRepository;
    private final InterviewerUserMapper interviewerUserMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Retrieves an interviewer user by their ID.
     *
     * @param id the ID of the interviewer user
     * @return the interviewer user DTO
     * @throws RuntimeException if the user is not found
     */
    @Override
    public InterviewerUserDto getById(UUID id) {
        Optional<InterviewerUser> interviewerUser = interviewerUserRepository.findById(id);

        if(interviewerUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        return interviewerUserMapper.toDto(interviewerUser.get());
    }

    /**
     * Retrieves a map of interviewer users by their last name.
     *
     * @param lastName the last name of the interviewer users
     * @return a map of interviewer user IDs to their DTOs
     */
    @Override
    public Map<UUID, InterviewerUserDto> getByLastName(String lastName) {
        return listToMap(interviewerUserRepository.findByLastName(lastName).stream()
                .map(interviewerUserMapper::toDto).toList());
    }

    /**
     * Retrieves an interviewer user by their email.
     *
     * @param email the email of the interviewer user
     * @return the interviewer user DTO
     * @throws RuntimeException if the user is not found
     */
    @Override
    public InterviewerUserDto getByEmail(String email) {
        Optional<InterviewerUser> interviewerUser = interviewerUserRepository.findByEmail(email);

        if(interviewerUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        return interviewerUserMapper.toDto(interviewerUser.get());
    }

    /**
     * Retrieves a map of interviewer users by their type.
     *
     * @param interviewerType the type of the interviewer users
     * @return a map of interviewer user IDs to their DTOs
     */
    @Override
    public Map<UUID, InterviewerUserDto> getByType(InterviewerType interviewerType) {
        return listToMap(interviewerUserRepository.findByType(interviewerType).stream()
                .map(interviewerUserMapper::toDto).toList());
    }

    /**
     * Retrieves a map of all interviewer users.
     *
     * @return a map of interviewer user IDs to their DTOs
     */
    @Override
    public Map<UUID, InterviewerUserDto> getAll() {
        return listToMap(interviewerUserRepository.findAll().stream()
                .map(interviewerUserMapper::toDto).toList());
    }

    /**
     * Adds all interviewer users from a given map.
     *
     * @param interviewerUserMap the map of interviewer user IDs to their DTOs
     */
    @Override
    public void addAllFromGivenMap(Map<UUID, InterviewerUserDto> interviewerUserMap) {
        interviewerUserRepository.saveAll(interviewerUserMap.values().stream()
                .map(interviewerUserMapper::toEntity).toList());
    }

    /**
     * Adds an interviewer user.
     *
     * @param interviewerUser the interviewer user DTO to add
     */
    @Override
    public void add(InterviewerUserDto interviewerUser) {
        interviewerUserRepository.save(interviewerUserMapper.toEntity(interviewerUser));
    }

    /**
     * Removes an interviewer user by their ID.
     *
     * @param id the ID of the interviewer user to remove
     * @throws RuntimeException if the user is not found
     */
    @Override
    public void removeElementById(UUID id) {
        Optional<InterviewerUser> interviewerUser = interviewerUserRepository.findById(id);

        if(interviewerUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        } else {
            interviewerUserRepository.delete(interviewerUser.get());
        }
    }

    /**
     * Saves an interviewer user element.
     *
     * @param interviewerUserDto the interviewer user DTO to save
     * @throws RuntimeException if the user is not found
     */
    @Override
    public void saveElement(InterviewerUserDto interviewerUserDto) {
        Optional<InterviewerUser> interviewerUser = interviewerUserRepository.findById(interviewerUserDto.getId());

        if(interviewerUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        } else {
            interviewerUserRepository.save(interviewerUserMapper.toEntity(interviewerUserDto));
        }
    }

    /**
     * Converts a list of interviewer user DTOs to a map of IDs to DTOs.
     *
     * @param interviewerUserDtoList the list of interviewer user DTOs
     * @return a map of interviewer user IDs to their DTOs
     */
    @Override
    public Map<UUID, InterviewerUserDto> listToMap(List<InterviewerUserDto> interviewerUserDtoList) {
        Map<UUID, InterviewerUserDto> interviewerUserDtoMap = new HashMap<>();

        for (InterviewerUserDto user : interviewerUserDtoList) {
            interviewerUserDtoMap.put(user.getId(), user);
        }

        return interviewerUserDtoMap;
    }

    /**
     * Creates an interviewer user.
     *
     * @param interviewerUserDto the interviewer user DTO to create
     * @return the created interviewer user DTO
     */
    @Override
    public InterviewerUserDto create(InterviewerUserDto interviewerUserDto) {
        if(interviewerUserDto.getId()==null)
            interviewerUserDto.setId(UUID.randomUUID());

        interviewerUserDto.setPassword(passwordEncoder.encode(interviewerUserDto.getPassword()));

        InterviewerUser interviewerEntity = interviewerUserMapper.toEntity(interviewerUserDto);
        interviewerUserRepository.save(interviewerEntity);
        return interviewerUserMapper.toDto(interviewerEntity);
    }

    /**
     * Retrieves a paginated list of all interviewer users.
     *
     * @param page the page number
     * @param size the number of items per page
     * @return a list of paginated interviewer user DTOs
     */
    @Override
    public List<InterviewerUserDto> getAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return interviewerUserRepository.findAll(pageRequest).stream()
                .map(interviewerUserMapper::toDto).toList();
    }
}
