package ro.hiringsystem.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mapper.CVMapper;
import ro.hiringsystem.mapper.CandidateUserMapper;
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

    @Override
    public CandidateUserDto getById(UUID id) {
        Optional<CandidateUser> candidateUser = candidateUserRepository.findById(id);

        if(candidateUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        return candidateUserMapper.toDto(candidateUser.get());
    }

    @Override
    public Map<UUID, CandidateUserDto> getByLastName(String lastName) {
        return listToMap(candidateUserRepository.findByLastName(lastName).stream()
                            .map(candidateUserMapper::toDto).toList());
    }

    @Override
    public CandidateUserDto getByEmail(String email) {
        Optional<CandidateUser> candidateUser = candidateUserRepository.findByEmail(email);

        if(candidateUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        return candidateUserMapper.toDto(candidateUser.get());
    }

    @Override
    public Map<UUID, CandidateUserDto> getAll() {
        return listToMap(candidateUserRepository.findAll().stream()
                            .map(candidateUserMapper::toDto).toList());
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, CandidateUserDto> candidateUserMap) {
        candidateUserRepository.saveAll(candidateUserMap.values().stream()
                            .map(candidateUserMapper::toEntity).toList());
    }

    @Override
    public void add(CandidateUserDto candidateUser) {
        candidateUserRepository.save(candidateUserMapper.toEntity(candidateUser));
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
        CandidateUser user = candidateUserMapper.toEntity(candidateUserDto);
        candidateUserRepository.save(candidateUserMapper.toEntity(candidateUserDto));
    }

    @Override
    public Map<UUID, CandidateUserDto> listToMap(List<CandidateUserDto> candidateUserDtoList) {
        Map<UUID, CandidateUserDto> candidateUserDtoMap = new HashMap<>();

        for (CandidateUserDto user : candidateUserDtoList) {
            candidateUserDtoMap.put(user.getId(), user);
        }

        return candidateUserDtoMap;
    }

    @Override
    public CandidateUserDto create(CandidateUserDto candidateUserDto) {
        if(candidateUserDto.getId()==null)
            candidateUserDto.setId(UUID.randomUUID());

        candidateUserDto.setPassword(passwordEncoder.encode(candidateUserDto.getPassword()));
        candidateUserDto.setCv(new CV());

        CandidateUser candidateEntity = candidateUserMapper.toEntity(candidateUserDto);
        candidateUserRepository.save(candidateEntity);
        return candidateUserMapper.toDto(candidateEntity);
    }

    @Override
    public CVDto getUserCV(UUID userId) {
        return cvMapper.toDto(candidateUserRepository.getUserCV(userId));
    }
}
