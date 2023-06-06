package ro.hiringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mapper.InterviewerUserMapper;
import ro.hiringsystem.model.entity.InterviewerUser;
import ro.hiringsystem.model.dto.InterviewerUserDto;
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

    @Override
    public InterviewerUserDto getById(UUID id) {
        Optional<InterviewerUser> interviewerUser = interviewerUserRepository.findById(id);

        if(interviewerUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        return interviewerUserMapper.toDto(interviewerUser.get());
    }

    @Override
    public Map<UUID, InterviewerUserDto> getByLastName(String lastName) {
        return listToMap(interviewerUserRepository.findByLastName(lastName).stream()
                .map(interviewerUserMapper::toDto).toList());
    }

    @Override
    public InterviewerUserDto getByEmail(String email) {
        Optional<InterviewerUser> interviewerUser = interviewerUserRepository.findByEmail(email);

        if(interviewerUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        return interviewerUserMapper.toDto(interviewerUser.get());
    }

    @Override
    public Map<UUID, InterviewerUserDto> getByType(InterviewerType interviewerType) {
        return listToMap(interviewerUserRepository.findByType(interviewerType).stream()
                .map(interviewerUserMapper::toDto).toList());
    }

    @Override
    public Map<UUID, InterviewerUserDto> getAll() {
        return listToMap(interviewerUserRepository.findAll().stream()
                .map(interviewerUserMapper::toDto).toList());
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, InterviewerUserDto> interviewerUserMap) {
        interviewerUserRepository.saveAll(interviewerUserMap.values().stream()
                .map(interviewerUserMapper::toEntity).toList());
    }

    @Override
    public void add(InterviewerUserDto interviewerUser) {
        interviewerUserRepository.save(interviewerUserMapper.toEntity(interviewerUser));
    }

    @Override
    public void removeElementById(UUID id) {
        Optional<InterviewerUser> interviewerUser = interviewerUserRepository.findById(id);

        if(interviewerUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        else interviewerUserRepository.delete(interviewerUser.get());
    }

    @Override
    public void saveElement(InterviewerUserDto interviewerUserDto) {
        Optional<InterviewerUser> interviewerUser = interviewerUserRepository.findById(interviewerUserDto.getId());

        if(interviewerUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        else interviewerUserRepository.save(interviewerUserMapper.toEntity(interviewerUserDto));
    }

    @Override
    public Map<UUID, InterviewerUserDto> listToMap(List<InterviewerUserDto> interviewerUserDtoList) {
        Map<UUID, InterviewerUserDto> interviewerUserDtoMap = new HashMap<>();

        for (InterviewerUserDto user : interviewerUserDtoList) {
            interviewerUserDtoMap.put(user.getId(), user);
        }

        return interviewerUserDtoMap;
    }

    @Override
    public InterviewerUserDto create(InterviewerUserDto interviewerUserDto) {
        if(interviewerUserDto.getId()==null)
            interviewerUserDto.setId(UUID.randomUUID());

        interviewerUserDto.setPassword(passwordEncoder.encode(interviewerUserDto.getPassword()));

        InterviewerUser interviewerEntity = interviewerUserMapper.toEntity(interviewerUserDto);
        interviewerUserRepository.save(interviewerEntity);
        return interviewerUserMapper.toDto(interviewerEntity);
    }
}
