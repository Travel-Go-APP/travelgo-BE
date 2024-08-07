package com.travelgo.backend.domain.visit.service;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.attraction.service.AttractionService;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.service.UserService;
import com.travelgo.backend.domain.visit.dto.VisitResponse;
import com.travelgo.backend.domain.visit.entity.Visit;
import com.travelgo.backend.domain.visit.repository.VisitRepository;
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
public class VisitService {
    private final UserService userService;
    private final AttractionService attractionService;
    private final VisitRepository visitRepository;

    @Transactional
    public VisitResponse saveAttractionAchievement(String email, Long attractionId) {
        Visit visit = createAttracitonAchievement(email, attractionId);
        Visit save = visitRepository.save(visit);
        return createAttractionAchievementResponse(save);
    }

    @Transactional
    public void deleteAttractionAchievement(String email, Long attractionId) {
        Visit visit = getAttractionAchievement(email, attractionId);
        visitRepository.delete(visit);
    }


    public List<VisitResponse> getList(String email, AreaCode areaCode) {
        User user = userService.getUser(email);
        List<Visit> achievementList = createAttractionAchievementList(areaCode, user);

        if (achievementList.isEmpty())
            throw new CustomException(ErrorCode.EMPTY_VALUE);

        return createAttractionAchievementResponseList(achievementList);
    }

    private Visit createAttracitonAchievement(String email, Long attractionId) {
        return Visit.builder()
                .user(userService.getUser(email))
                .attraction(attractionService.getAttraction(attractionId))
                .build();
    }

    private Visit getAttractionAchievement(String email, Long attractionId) {
        return visitRepository.findByUserAndAttraction(
                userService.getUser(email),
                attractionService.getAttraction(attractionId)
        );
    }

    private List<Visit> createAttractionAchievementList(AreaCode areaCode, User user) {
        return visitRepository.findAllByUserAndAttraction_Area(user, areaCode);
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