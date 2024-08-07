package com.travelgo.backend.domain.user.service;


import com.travelgo.backend.domain.user.dto.Request.MainPageRequest;
import com.travelgo.backend.domain.user.dto.Request.UserRequest;
import com.travelgo.backend.domain.user.dto.Response.MainPageResponse;
import com.travelgo.backend.domain.user.dto.Response.UserDto;
import com.travelgo.backend.domain.user.dto.Response.UserResponse;
import com.travelgo.backend.domain.user.entity.Role;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.entity.UserExp;
import com.travelgo.backend.domain.user.exception.UserException;
import com.travelgo.backend.domain.user.repository.UserRepository;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GeoCodingService geoCodingService;

    BadWordFiltering badWordFiltering = new BadWordFiltering();

    @Transactional
    public void signUp(UserRequest.SignUp request){
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if(existingUser.isPresent()){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        User newUser = User.createUser(request);

        userRepository.save(newUser);
    }


    //수정된 로그인 서비스
    @Transactional
    public void login(String email) {
        User user = getUser(email);

        // 로그인 성공 시 아무 작업도 하지 않음
    }


    public void checkNicknameValidity(@Valid String nickName){
        if(userRepository.findByNickname(nickName).isPresent()){
            throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
        }else if(badWordFiltering.check(nickName)){
            throw new CustomException(ErrorCode.INCLUDE_SLANG);
        }
    }

    @Transactional
    public UserResponse.UpdateNickname updateUser(String email, String nickName){
        User user = getUser(email);
        checkNicknameValidity(nickName);

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
    public UserResponse.DeleteUser deleteUser(String email){
        User user = getUser(email);
        userRepository.delete(user);

        return new UserResponse.DeleteUser(user.getEmail() + "유저가 삭제 되었습니다.");
    }

    public  User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
    }

    public MainPageResponse getMainPageResponse(MainPageRequest request){
        User user = getUser(request.getEmail());

        String area;

        try{
            area = geoCodingService.reverseGeocode(request.getLatitude(), request.getLongitude());

            if(area == null){
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
        String visitingBenefit = "경험치 2배";
        int maxSearch = 10;

        return new MainPageResponse(
                user.getNickname(),
                user.getLevel(),
                user.getExperience(),
                nextLevelExp,
                percentage,
                area,
                visitingBenefit,
                user.getShoes(),
                user.getMaxSearch(),
                user.getPossibleSearch(),
                user.getQuest(),
                user.getDetectionRange(),
                user.getExperienceX(),
                user.getTgX(),
                user.getTg(),
                user.getWorkCount()
        );
    }

    private UserResponse createResponse(User user){
        return UserResponse.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
//                .phoneNumber(user.getPhoneNumber())
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

    // oauth user service
    public UserDto userInfo(String email) {
        User user = findByEmailOrThrow(email);
        return UserDto.fromEntity(user);
    }

//    @Transactional
//    public UserDto userEdit(UserEditRequest request, String email) {
//        User user = findByEmailOrThrow(email);
//        user.updateUser(request);
//        return UserDto.fromEntity(user);
//    }

    @Transactional
    public Role changeAdmin(String email) {
        User user = findByEmailOrThrow(email);
        user.changeAdmin();
        return user.getRole();
    }

    public List<UserDto> getList() {
        return userRepository.findAll().stream()
                .map(UserDto::fromEntity)
                .toList();
    }

    private User findByEmailOrThrow(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.NOT_FOUND_USER));
    }
}
