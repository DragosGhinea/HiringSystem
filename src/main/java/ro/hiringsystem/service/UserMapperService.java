package ro.hiringsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.hiringsystem.mapper.CandidateUserMapper;
import ro.hiringsystem.mapper.InterviewerUserMapper;
import ro.hiringsystem.mapper.ManagerUserMapper;
import ro.hiringsystem.model.entity.CandidateUser;
import ro.hiringsystem.model.entity.InterviewerUser;
import ro.hiringsystem.model.entity.ManagerUser;
import ro.hiringsystem.model.abstracts.User;
import ro.hiringsystem.model.dto.CandidateUserDto;
import ro.hiringsystem.model.dto.InterviewerUserDto;
import ro.hiringsystem.model.dto.ManagerUserDto;
import ro.hiringsystem.model.dto.UserDto;

@Service
@RequiredArgsConstructor
public class UserMapperService {
    private final CandidateUserMapper candidateUserMapper;
    private final InterviewerUserMapper interviewerUserMapper;
    private final ManagerUserMapper managerUserMapper;
    
    public UserDto toDto(User user){
        if(user instanceof CandidateUser instancedUser){
            return candidateUserMapper.toDto(instancedUser);
        }
        else if(user instanceof InterviewerUser instancedUser){
            return interviewerUserMapper.toDto(instancedUser);
        }
        else{
            return managerUserMapper.toDto((ManagerUser) user);
        }
    }

    public User toEntity(UserDto userDto){
        if(userDto instanceof CandidateUserDto instancedUser){
            return candidateUserMapper.toEntity(instancedUser);
        }
        else if(userDto instanceof InterviewerUserDto instancedUser){
            return interviewerUserMapper.toEntity(instancedUser);
        }
        else{
            return managerUserMapper.toEntity((ManagerUserDto) userDto);
        }
    }

}