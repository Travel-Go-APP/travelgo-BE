package com.travelgo.backend.domain.attraction.service;

import com.travelgo.backend.domain.attraction.dto.AttractionResponse;
import com.travelgo.backend.domain.attraction.entity.Attraction;
import com.travelgo.backend.domain.visit.entity.Visit;
import com.travelgo.backend.domain.visit.repository.VisitRepository;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.service.UserService;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionRecordService {
    private final UserService userService;
    private final AttractionService attractionService;
    private final VisitRepository visitRepository;

    public List<AttractionResponse> getunVisitAttractionWithInDistance(String email, Double latitude, Double longitude, Double distance) {
        User user = userService.getUser(email);

        // 주변에 명소 리스트
        List<Attraction> attractions = attractionService.getAttractionsWithInDistance(latitude, longitude, distance);

        //filter를 통해서 방문하지 않은 명소를 가져온다.
        List<Visit> achievementList = visitRepository.findAllByUser(user);

        List<AttractionResponse> result = attractions.stream()
                .filter(attraction -> achievementList.stream()
                        .noneMatch(achievement -> achievement.getAttraction().equals(attraction)))
                .map(AttractionResponse::new)
                .collect(Collectors.toList());

        checkEmpty(result);

        return result;
    }

    private static void checkEmpty(List<?> list) {
        if (list.isEmpty())
            throw new CustomException(ErrorCode.EMPTY_VALUE);
    }
}
