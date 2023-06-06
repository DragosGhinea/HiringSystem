package ro.hiringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mapper.ManagerUserMapper;
import ro.hiringsystem.model.entity.ManagerUser;
import ro.hiringsystem.model.dto.ManagerUserDto;
import ro.hiringsystem.repository.ManagerUserRepository;
import ro.hiringsystem.service.ManagerUserService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ManagerUserServiceImpl implements ManagerUserService {

    private final ManagerUserRepository managerUserRepository;
    private final ManagerUserMapper managerUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ManagerUserDto getById(UUID id) {
        Optional<ManagerUser> managerUser = managerUserRepository.findById(id);

        if(managerUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        return managerUserMapper.toDto(managerUser.get());
    }

    @Override
    public Map<UUID, ManagerUserDto> getByLastName(String lastName) {
        return listToMap(managerUserRepository.findByLastName(lastName).stream()
                .map(managerUserMapper::toDto).toList());
    }

    @Override
    public ManagerUserDto getByEmail(String email) {
        Optional<ManagerUser> managerUser = managerUserRepository.findByEmail(email);

        if(managerUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        return managerUserMapper.toDto(managerUser.get());
    }

    @Override
    public Map<UUID, ManagerUserDto> getAll() {
        return listToMap(managerUserRepository.findAll().stream()
                .map(managerUserMapper::toDto).toList());
    }

    @Override
    public void addAllFromGivenMap(Map<UUID, ManagerUserDto> managerUserMap) {
        managerUserRepository.saveAll(managerUserMap.values().stream()
                .map(managerUserMapper::toEntity).toList());
    }

    @Override
    public void add(ManagerUserDto managerUser) {
        managerUserRepository.save(managerUserMapper.toEntity(managerUser));
    }

    @Override
    public void removeElementById(UUID id) {
        Optional<ManagerUser> managerUser = managerUserRepository.findById(id);

        if(managerUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        else managerUserRepository.delete(managerUser.get());
    }

    @Override
    public void saveElement(ManagerUserDto managerUserDto) {
        Optional<ManagerUser> managerUser = managerUserRepository.findById(managerUserDto.getId());

        if(managerUser.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        else managerUserRepository.save(managerUserMapper.toEntity(managerUserDto));
    }

    @Override
    public Map<UUID, ManagerUserDto> listToMap(List<ManagerUserDto> managerUserDtoList) {
        Map<UUID, ManagerUserDto> managerUserDtoMap = new HashMap<>();

        for (ManagerUserDto user : managerUserDtoList) {
            managerUserDtoMap.put(user.getId(), user);
        }

        return managerUserDtoMap;
    }

    @Override
    public ManagerUserDto create(ManagerUserDto managerUserDto) {
        if(managerUserDto.getId()==null)
            managerUserDto.setId(UUID.randomUUID());

        managerUserDto.setPassword(passwordEncoder.encode(managerUserDto.getPassword()));

        ManagerUser managerEntity = managerUserMapper.toEntity(managerUserDto);
        managerUserRepository.save(managerEntity);
        return managerUserMapper.toDto(managerEntity);
    }
}