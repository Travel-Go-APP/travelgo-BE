package com.travelgo.backend.domain.user.service;


import com.travelgo.backend.domain.user.dto.Request.UserRequest;
import com.travelgo.backend.domain.user.dto.Response.UserResponse;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.exception.UserAlreadyExistsException;
import com.travelgo.backend.domain.user.exception.UserNotFoundException;
import com.travelgo.backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final GeoCodingService geoCodingService;

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
    public UserResponse.Login login(UserRequest.Login request){
        User user = userRepository.findByEmail(request.getEmail());

        if(user == null){
            throw new UserNotFoundException(request.getEmail()+"유저가 존재하지 않습니다.");
        }

        logger.info("Received login request with latitude: {}, longitude: {}", request.getLatitude(), request.getLongitude());

        String region;

        try{
            region = geoCodingService.reverseGeocode(request.getLatitude(), request.getLongitude());

            if(region == null){
                throw new IllegalArgumentException("유효한 지역 정보를 찾을 수 없습니다");
            }
        } catch (Exception e){
            logger.error("GeoCodingService error: ", e);
            throw new IllegalArgumentException("유효한 지역 정보를 찾을 수 없습니다.", e);
        }


        return new UserResponse.Login(user.getUsername(), user.getLevel(), user.getQuest(), region, user.getEmail(), user.getDetectionRange());
    }

    public UserResponse getUser(String email){
        return createResponse(email);
    }

    public boolean CheckNicknameExists(UserRequest.CheckNickname request){
        return userRepository.findByNickname(request.getNickname()).isPresent();
    }

    @Transactional
    public UserResponse.UpdateNickname updateUser(UserRequest.UpdateNickname request){
        User user = userRepository.findByEmail(request.getEmail());
        if(user == null){
            throw new UserNotFoundException(request.getEmail());
        }
        if(request.getNewNickname() != null && !request.getNewNickname().isEmpty()){
            user.setNickname(request.getNewNickname());
        }
        userRepository.save(user);
        return new UserResponse.UpdateNickname(user.getEmail(), user.getNickname());
    }

    @Transactional
    public UserResponse.DeleteUser deleteUser(UserRequest.DeleteUser request){
        User user = userRepository.findByEmail(request.getEmail());
        if(user == null){
            throw new UserNotFoundException(request.getEmail());
        }
        userRepository.delete(user);
        return new UserResponse.DeleteUser(request.getEmail()+"유저가 삭제 되었습니다.");
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
