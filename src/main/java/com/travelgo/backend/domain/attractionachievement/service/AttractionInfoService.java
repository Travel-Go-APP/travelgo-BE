package com.travelgo.backend.domain.attractionachievement.service;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.attraction.repository.AttractionRepository;
import com.travelgo.backend.domain.attractionachievement.dto.AllInfo;
import com.travelgo.backend.domain.attractionachievement.dto.AttractionInfo;
import com.travelgo.backend.domain.attractionachievement.repository.AttractionAchievementRepository;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionInfoService {

    private final AttractionAchievementRepository attractionAchievementRepository;
    private final AttractionRepository attractionRepository;
    private final UserService userService;

    public AllInfo getAllInfo(String email) {
        User user = userService.getUser(email);

        Long total = attractionRepository.count();
        Long visit = attractionAchievementRepository.countByUser(user);

        return AllInfo.builder()
                .totalCount(total)
                .visitCount(visit)
                .build();
    }

    public List<AttractionInfo> getAttractionInfo(String email) {
        User user = userService.getUser(email);

        List<AttractionInfo> attractionInfoList = new ArrayList<>();

        for (AreaCode area : AreaCode.values()) {
            Long total = attractionRepository.countByArea(area);
            Long visit = attractionAchievementRepository.countByUserAndAttraction_Area(user, area);

            AttractionInfo attractionInfo = AttractionInfo.builder()
                    .area(area)
                    .totalCount(total)
                    .visitCount(visit)
                    .build();
            attractionInfoList.add(attractionInfo);
        }

        return attractionInfoList;
    }
}
