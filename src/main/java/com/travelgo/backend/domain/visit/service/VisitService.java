package com.travelgo.backend.domain.attractionachievement.service;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.attraction.service.AttractionService;
import com.travelgo.backend.domain.visit.dto.AttractionAchievementRequest;
import com.travelgo.backend.domain.visit.dto.VisitResponse;
import com.travelgo.backend.domain.visit.entity.Visit;
import com.travelgo.backend.domain.visit.repository.AttractionAchievementRepository;
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
    public VisitResponse saveAttractionAchievement(AttractionAchievementRequest request) {
        Visit visit = createAttracitonAchievement(request);
        Visit save = attractionAchievementRepository.save(visit);
        return createAttractionAchievementResponse(save);
    }

    @Transactional
    public void deleteAttractionAchievement(AttractionAchievementRequest request) {
        Visit visit = getAttractionAchievement(request);
        attractionAchievementRepository.delete(visit);
    }


    public List<VisitResponse> getList(String email, AreaCode areaCode) {
        User user = userService.getUser(email);
        List<Visit> achievementList = createAttractionAchievementList(areaCode, user);

        if (achievementList.isEmpty())
            throw new CustomException(ErrorCode.EMPTY_VALUE);

        return createAttractionAchievementResponseList(achievementList);
    }

    private Visit createAttracitonAchievement(AttractionAchievementRequest request) {
        return Visit.builder()
                .user(userService.getUser(request.getEmail()))
                .attraction(attractionService.getAttraction(request.getAttractionId()))
                .build();
    }

    private Visit getAttractionAchievement(AttractionAchievementRequest request) {
        return attractionAchievementRepository.findByUserAndAttraction(
                userService.getUser(request.getEmail()),
                attractionService.getAttraction(request.getAttractionId())
        );
    }

    private List<Visit> createAttractionAchievementList(AreaCode areaCode, User user) {
        return attractionAchievementRepository.findAllByUserAndAttraction_Area(user, areaCode);
    }

    private VisitResponse createAttractionAchievementResponse(Visit visit) {
        return VisitResponse.of(visit);
    }

    private List<VisitResponse> createAttractionAchievementResponseList(List<Visit> visitList) {
        return visitList.stream()
                .map(VisitResponse::new)
                .toList();

    }

}