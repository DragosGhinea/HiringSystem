package ro.hiringsystem.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mapper.CandidateUserMapper;
import ro.hiringsystem.model.entity.CandidateUser;
import ro.hiringsystem.model.dto.CandidateUserDto;
import ro.hiringsystem.repository.CandidateUserRepository;
import ro.hiringsystem.service.CandidateUserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CandidateUserServiceImpl implements CandidateUserService {

    private final CandidateUserRepository candidateUserRepository;

    @Override
    public CandidateUserDto getById(UUID id) {
        Optional<CandidateUser> candidateUser = candidateUserRepository.findById(id);

        if(candidateUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        return CandidateUserMapper.INSTANCE.toDto(candidateUser.get());
    }

    @Override
    public Map<UUID, CandidateUserDto> getByLastName(String lastName) {
        return listToMap(candidateUserRepository.findByLastName(lastName).stream()
                            .map(CandidateUserMapper.INSTANCE::toDto).toList());
    }

    @Override
    public CandidateUserDto getByEmail(String email) {
        Optional<CandidateUser> candidateUser = candidateUserRepository.findByEmail(email);

        if(candidateUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        return CandidateUserMapper.INSTANCE.toDto(candidateUser.get());
    }

    @Override
    public Map<UUID, CandidateUserDto> getAll() {
        return listToMap(candidateUserRepository.findAll().stream()
                            .map(CandidateUserMapper.INSTANCE::toDto).toList());
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, CandidateUserDto> candidateUserMap) {
        candidateUserRepository.saveAll(candidateUserMap.values().stream()
                            .map(CandidateUserMapper.INSTANCE::toEntity).toList());
    }

    @Override
    public void add(CandidateUserDto candidateUser) {
        candidateUserRepository.save(CandidateUserMapper.INSTANCE.toEntity(candidateUser));
    }

    @Override
    public void removeElementById(UUID id) {
        Optional<CandidateUser> candidateUser = candidateUserRepository.findById(id);

        if(candidateUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        else candidateUserRepository.delete(candidateUser.get());
    }

    @Override
    public void saveElement(CandidateUserDto candidateUserDto) {
        CandidateUser user = CandidateUserMapper.INSTANCE.toEntity(candidateUserDto);
        candidateUserRepository.save(CandidateUserMapper.INSTANCE.toEntity(candidateUserDto));
    }

    @Override
    public Map<UUID, CandidateUserDto> listToMap(List<CandidateUserDto> candidateUserDtoList) {
        Map<UUID, CandidateUserDto> candidateUserDtoMap = new HashMap<>();

        for (CandidateUserDto user : candidateUserDtoList) {
            candidateUserDtoMap.put(user.getId(), user);
        }

        return candidateUserDtoMap;
    }

}
