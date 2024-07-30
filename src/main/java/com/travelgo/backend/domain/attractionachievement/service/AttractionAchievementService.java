package com.travelgo.backend.domain.attractionachievement.service;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.attraction.repository.AttractionRepository;
import com.travelgo.backend.domain.attractionachievement.dto.AttractionAllInfo;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.service.UserService;
import com.travelgo.backend.domain.visit.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionAchievementService {

    private final VisitRepository visitRepository;
    private final AttractionRepository attractionRepository;
    private final UserService userService;

    public Map<String, Object> getAttractionAchievement(String email) {
        User user = userService.getUser(email);

        // AttractionAllInfo 생성
        Long total = attractionRepository.count();
        Long visit = visitRepository.countByUser(user);
        AttractionAllInfo allInfo = AttractionAllInfo.builder()
                .totalCount(total)
                .visitCount(visit)
                .build();

        Map<AreaCode, Long> areaTotalCount = new HashMap<>();
        Map<AreaCode, Long> areaVisitCount = new HashMap<>();

        for (AreaCode area : AreaCode.values()) {
            Long areaTotal = attractionRepository.countByArea(area);
            Long areaVisit = visitRepository.countByUserAndAttraction_Area(user, area);

            areaTotalCount.put(area, areaTotal);
            areaVisitCount.put(area, areaVisit);
        }

        // Map에 두 정보를 담아서 반환
        Map<String, Object> achievementInfo = new HashMap<>();
        achievementInfo.put("all", allInfo);
        achievementInfo.put("areaTotalCount", areaTotalCount);
        achievementInfo.put("areaVisitCount", areaVisitCount);

        return achievementInfo;
    }

}
