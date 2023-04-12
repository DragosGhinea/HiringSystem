package ro.hiringsystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mappers.UserMapper;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.dto.UserDto;
import ro.hiringsystem.repository.UserRepository;
import ro.hiringsystem.service.UserService;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService<UserDto> {

    private final UserRepository userRepository;

    @Override
    public UserDto getById(UUID id) {
        var user = userRepository.findById(id).get();

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());

        return userDto;
    }

    @Override
    public Map getAllFromMap() {
        return null;
    }

    @Override
    public void add(UserDto user) {

    }

    @Override
    public void add(User user) {

    }

    @Override
    public void removeElementById(UUID id) {

    }

    @Override
    public Boolean updateElementById(UUID id, UserDto newUser) {
        return null;
    }

    @Override
    public UserDto getUserByFirstName(String firstName) {
        Optional<User> userOptional = userRepository.findByFirstName(firstName);

        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found!");
        }

        User user = userOptional.get();

        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    public void addAllFromGivenMap(Map clientMap) {

    }
}
