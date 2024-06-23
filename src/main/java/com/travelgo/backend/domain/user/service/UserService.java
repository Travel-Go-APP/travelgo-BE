package com.travelgo.backend.domain.user.service;


import com.travelgo.backend.domain.user.dto.Request.UserRequest;
import com.travelgo.backend.domain.user.dto.Response.UserResponse;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.exception.UserAlreadyExistsException;
import com.travelgo.backend.domain.user.exception.UserNotFoundException;
import com.travelgo.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Transactional
    public UserResponse signUp(UserRequest.SignUp request){
        User user = userRepository.findByEmail(request.getEmail());

        if(user != null){
            throw new UserAlreadyExistsException(request.getEmail()+"을 가진 유저가 이미 존재합니다.");
        }

        user = User.createUser(request);

        userRepository.save(user);
        return getUser(user.getEmail());
    }

    @Transactional
    public UserResponse login(UserRequest.login request){
        User user = userRepository.findByEmail(request.getEmail());

        if(user != null){
            return getUser(user.getEmail());
        }else{
            throw new UserNotFoundException(request.getEmail()+"유저가 존재하지 않습니다.");
        }
    }

    public UserResponse getUser(String email){
        return createResponse(email);
    }

    public boolean CheckNicknameExists(UserRequest.CheckNickname request){
        return userRepository.findByNickname(request.getNickname()).isPresent();
    }

    @Transactional
    public UserResponse.Update updateUser(UserRequest.updateNickname request){
        User user = userRepository.findByEmail(request.getEmail());
        if(user == null){
            throw new UserNotFoundException(request.getEmail());
        }
        if(request.getNewNickname() != null && !request.getNewNickname().isEmpty()){
            user.setNickname(request.getNewNickname());
        }
        userRepository.save(user);
        return new UserResponse.Update(user.getEmail(), user.getNickname());
    }

    private UserResponse createResponse(String email){
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UserNotFoundException(email+"을 가진 유저를 찾을 수 없습니다.");
        }
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .detectionRange(user.getDetectionRange())
                .experience(user.getExperience())
                .workCount(user.getWorkCount())
                .level(user.getLevel())
                .quest(user.getQuest())
                .tg(user.getTg())
                .build();
    }
}
