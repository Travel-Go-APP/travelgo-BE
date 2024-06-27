package com.travelgo.backend.domain.user.service;


import com.travelgo.backend.domain.user.dto.Request.UserRequest;
import com.travelgo.backend.domain.user.dto.Response.UserResponse;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.entity.UserExp;
import com.travelgo.backend.domain.user.repository.UserRepository;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final GeoCodingService geoCodingService;

    @Transactional
    public void signUp(UserRequest.SignUp request){
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if(existingUser.isPresent()){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        User newUser = User.createUser(request);

        userRepository.save(newUser);
    }

    /*@Transactional
    public UserResponse.Login login(UserRequest.Login request){
        User user = getUser(request.getEmail());

        String region;

        try{
            region = geoCodingService.reverseGeocode(request.getLatitude(), request.getLongitude());

            if(region == null){
                throw new CustomException(ErrorCode.NOT_FOUND_AREA);
            }
        } catch (Exception e){
            throw new CustomException(ErrorCode.NOT_FOUND_AREA);
        }

        int[] expTable = UserExp.getExpTable();
        int currentLevel = user.getLevel();
        int currentExperience = user.getExperience();
        int nextLevelExp = expTable[currentLevel];
        double percentage = (double) currentExperience / nextLevelExp * 100;


        return new UserResponse.Login(
                user.getUsername(),
                user.getLevel(),
                user.getExperience(),
                nextLevelExp,
                percentage,
                user.getQuest(),
                region,
                user.getEmail(),
                user.getDetectionRange(),
                user.getShoes(),
                user.getBag());
    }*/

    //수정된 로그인 서비스
    @Transactional
    public void login(String email) {
        User user = getUser(email);

        // 로그인 성공 시 아무 작업도 하지 않음
    }


    public boolean CheckNicknameExists(@Valid String nickName){
        return userRepository.findByNickname(nickName).isPresent();
    }

    @Transactional
    public UserResponse.UpdateNickname updateUser(String email, String nickName){
        User user = getUser(email);
        user.changeNickname(nickName);

        userRepository.save(user);

        return new UserResponse.UpdateNickname(user.getEmail(), user.getNickname());
    }

    @Transactional
    public UserResponse.UpdateExp updateExp(UserRequest.UpdateExp request){
        User user = getUser(request.getEmail());

        user.addExperience(request.getExperience());

        boolean levelUp = false;
        int[] expTable = UserExp.getExpTable();

        while(user.getExperience() >= expTable[user.getLevel()]){
            user.reduceExperience(expTable[user.getLevel()]);
            user.levelUp();
            levelUp = true;
        }

        userRepository.save(user);

        int currentExperience = user.getExperience();
        int nextLevelExp = expTable[user.getLevel()];
        double percentage = (double) currentExperience / nextLevelExp * 100;

        return new UserResponse.UpdateExp(user.getEmail(), currentExperience, nextLevelExp, percentage, levelUp);
    }

    @Transactional
    public UserResponse.DeleteUser deleteUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        userRepository.delete(user);

        return new UserResponse.DeleteUser(user.getEmail() + "유저가 삭제 되었습니다.");
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
    }

    private UserResponse createResponse(User user){
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
                .shoes(user.getShoes())
                .bag(user.getBag())
                .build();
    }
}
