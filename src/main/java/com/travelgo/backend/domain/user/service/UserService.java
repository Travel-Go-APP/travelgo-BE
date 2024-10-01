package com.travelgo.backend.domain.user.service;


import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.attraction.repository.LikesRepository;
import com.travelgo.backend.domain.event.dto.VisitCountEventDto;
import com.travelgo.backend.domain.event.service.VisitCountEventService;
import com.travelgo.backend.domain.review.repository.ReviewRepository;
import com.travelgo.backend.domain.user.dto.AgreeDto;
import com.travelgo.backend.domain.user.dto.Request.MainPageRequest;
import com.travelgo.backend.domain.user.dto.Request.UserRequest;
import com.travelgo.backend.domain.user.dto.Response.MainPageResponse;
import com.travelgo.backend.domain.user.dto.Response.UserResponse;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.entity.UserExp;
import com.travelgo.backend.domain.user.repository.UserAgreeRepository;
import com.travelgo.backend.domain.user.repository.UserRepository;
import com.travelgo.backend.domain.userItems.repository.UserItemsRepository;
import com.travelgo.backend.domain.userItems.service.UserItemsService;
import com.travelgo.backend.domain.util.entity.WeatherApiExplorer;
import com.travelgo.backend.domain.util.entity.filter.BadWordFiltering;
import com.travelgo.backend.domain.util.entity.geo.service.GeoCodingService;
import com.travelgo.backend.domain.visit.repository.VisitRepository;
import com.travelgo.backend.domain.weather.dto.WeatherDto;
import com.travelgo.backend.domain.weather.service.WeatherService;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserItemsService userItemsService;
    private final UserRepository userRepository;
    private final UserAgreeRepository userAgreeRepository;
    private final VisitCountEventService visitCountEventService;
    private final GeoCodingService geoCodingService;
    private final WeatherService weatherService;

    private final LikesRepository likesRepository;
    private final ReviewRepository reviewRepository;
    private final UserItemsRepository userItemsRepository;
    private final VisitRepository visitRepository;

    @PersistenceContext
    private final EntityManager entityManager;


    BadWordFiltering badWordFiltering = new BadWordFiltering();

    @Transactional
    public void signUp(UserRequest.SignUp request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        User newUser = User.createUser(request);

        userRepository.save(newUser);
    }


    //수정된 로그인 서비스
    @Transactional
    public void login(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
    }


    public void checkNicknameValidity(@Valid String nickName) {
        if (userRepository.findByNickname(nickName).isPresent()) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_USER);
        } else if (badWordFiltering.check(nickName)) {
            throw new CustomException(ErrorCode.INCLUDE_SLANG);
        }
    }

    @Transactional
    public UserResponse.UpdateNickname updateUser(String email, String nickName) {
        User user = getUser(email);
        checkNicknameValidity(nickName);

        user.changeNickname(nickName);
        userRepository.save(user);

        return new UserResponse.UpdateNickname(user.getEmail(), user.getNickname());
    }

    @Transactional
    public UserResponse.UpdateExp updateExp(UserRequest.UpdateExp request) {
        User user = getUser(request.getEmail());

        user.addExperience(request.getExperience());

        boolean levelUp = false;
        int[] expTable = UserExp.getExpTable();

        while (user.getExperience() >= expTable[user.getLevel()]) {
            user.reduceExperience(expTable[user.getLevel()]);
            user.levelUp();
            levelUp = true;
        }

        userRepository.save(user);

        int currentExperience = user.getExperience();
        int nextLevelExp = expTable[user.getLevel()];
        double percentage = (double) currentExperience / nextLevelExp * 100;

        return new UserResponse.UpdateExp(user.getEmail(), user.getLevel(), currentExperience, nextLevelExp, percentage, levelUp);
    }

    @Transactional
    public UserResponse.DeleteUser deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        Long userId = user.getUserId();

        // 관련된 테이블의 데이터 삭제
        likesRepository.deleteByUser_UserId(userId);
        reviewRepository.deleteByUser_UserId(userId);
        userItemsRepository.deleteByUser_UserId(userId);
        visitRepository.deleteByUser_UserId(userId);

        // 사용자 삭제
        userRepository.delete(user);

        // Optional: Flush 호출
        entityManager.flush();

        return new UserResponse.DeleteUser(user.getEmail() + " 유저가 삭제 되었습니다.");
    }


    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
    }

    @Transactional
    public void saveAgree(String email, AgreeDto agreeDto) {
        User user = getUser(email);
        user.saveAgree(agreeDto);
        userAgreeRepository.save(user.getUserAgree());
//        return AgreeDto.of(user.getUserAgree());
    }

    @Transactional
    public Map<String, Object> missionAchieve(String email, Double latitude, Double longitude) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));

        Map<String, Object> response = new HashMap<>();
        int[] expTable = UserExp.getExpTable();

        if (user.getQuest() == 0) {
            // 1,000 경험치 추가
            int experience = 1000;
            int nextLevelExp = expTable[user.getLevel()];

            user.addExperience(experience);

            double percentage = (double) user.getExperience() / nextLevelExp * 100;

            response.put("level", user.getLevel());
            response.put("experience", user.getExperience());
            response.put("nextLevelExp", nextLevelExp);
            response.put("percentage", percentage);

            // 아이템 지급 (기존 add 메서드를 호출)
            Map<String, Object> itemResponse = userItemsService.add(email, latitude, longitude);
            response.putAll(itemResponse);

            // 퀘스트 상태를 1로 업데이트
            user.setQuest(1);
            userRepository.save(user);
        } else {
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        return response;  // 보상과 아이템 정보를 함께 반환
    }

    @Transactional
    public MainPageResponse getMainPageResponse(MainPageRequest request) throws IOException {
        User user = getUser(request.getEmail());

        String[] areaAndVisitArea;

        try {
            areaAndVisitArea = geoCodingService.reverseGeocode(request.getLatitude(), request.getLongitude());

            if (areaAndVisitArea == null || areaAndVisitArea[0] == null) {
                throw new CustomException(ErrorCode.NOT_FOUND_AREA);
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.NOT_FOUND_AREA);
        }

        int[] expTable = UserExp.getExpTable();
        int currentLevel = user.getLevel();
        int currentExperience = user.getExperience();
        int nextLevelExp = expTable[currentLevel];
        double percentage = (double) currentExperience / nextLevelExp * 100;

        AreaCode areaCode = visitCountEventService.getAreaCode(areaAndVisitArea[0]);

        VisitCountEventDto benefit = visitCountEventService.getBenefit(request.getEmail(), areaCode);

        // 현재 날짜와 시간 가져오기
        LocalDateTime base = LocalDateTime.now().minusDays(1);;

        // 날짜 형식 지정
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String date = base.format(dateFormatter);

        String result = WeatherApiExplorer.getWeatherInfo(300, 1, request.getDate(), "0200", (int) request.getLatitude(), (int) request.getLongitude());
        WeatherDto weatherDto = weatherService.weatherInit(result);

        return new MainPageResponse(
                user.getNickname(),
                user.getLevel(),
                user.getExperience(),
                nextLevelExp,
                percentage,
                areaAndVisitArea[0],
                areaAndVisitArea[1],
                benefit.getVisitBenefit(),
                user.getShoes(),
                user.getMaxSearch(),
                user.getPossibleSearch(),
                user.getQuest(),
                user.getDetectionRange(),
                user.getExperienceX(),
                user.getTgX(),
                user.getTg(),
                user.getWorkCount(),
                weatherDto
        );
    }
}
