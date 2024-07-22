package com.travelgo.backend.domain.attractionachievement.service;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.attraction.service.AttractionService;
import com.travelgo.backend.domain.attractionachievement.dto.AttractionAchievementRequest;
import com.travelgo.backend.domain.attractionachievement.dto.AttractionAchievementResponse;
import com.travelgo.backend.domain.attractionachievement.entity.AttractionAchievement;
import com.travelgo.backend.domain.attractionachievement.repository.AttractionAchievementRepository;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.service.UserService;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionAchievementService {
    private final UserService userService;
    private final AttractionService attractionService;
    private final AttractionAchievementRepository attractionAchievementRepository;

    @Transactional
    public AttractionAchievementResponse saveAttractionAchievement(AttractionAchievementRequest request) {
        AttractionAchievement attractionAchievement = createAttracitonAchievement(request);
        AttractionAchievement save = attractionAchievementRepository.save(attractionAchievement);
        return createAttractionAchievementResponse(save);
    }

    @Transactional
    public void deleteAttractionAchievement(AttractionAchievementRequest request) {
        AttractionAchievement attractionAchievement = getAttractionAchievement(request);
        attractionAchievementRepository.delete(attractionAchievement);
    }


    public List<AttractionAchievementResponse> getList(String email, AreaCode areaCode) {
        User user = userService.getUser(email);
        List<AttractionAchievement> achievementList = createAttractionAchievementList(areaCode, user);

        if (achievementList.isEmpty())
            throw new CustomException(ErrorCode.EMPTY_VALUE);

        return createAttractionAchievementResponseList(achievementList);
    }

    private AttractionAchievement createAttracitonAchievement(AttractionAchievementRequest request) {
        return AttractionAchievement.builder()
                .user(userService.getUser(request.getEmail()))
                .attraction(attractionService.getAttraction(request.getAttractionId()))
                .build();
    }

    private AttractionAchievement getAttractionAchievement(AttractionAchievementRequest request) {
        return attractionAchievementRepository.findByUserAndAttraction(
                userService.getUser(request.getEmail()),
                attractionService.getAttraction(request.getAttractionId())
        );
    }

    private List<AttractionAchievement> createAttractionAchievementList(AreaCode areaCode, User user) {
        return attractionAchievementRepository.findAllByUserAndAttraction_Area(user, areaCode);
    }

    private AttractionAchievementResponse createAttractionAchievementResponse(AttractionAchievement attractionAchievement) {
        return AttractionAchievementResponse.of(attractionAchievement);
    }

    private List<AttractionAchievementResponse> createAttractionAchievementResponseList(List<AttractionAchievement> attractionAchievementList) {
        return attractionAchievementList.stream()
                .map(AttractionAchievementResponse::new)
                .toList();

    }

}