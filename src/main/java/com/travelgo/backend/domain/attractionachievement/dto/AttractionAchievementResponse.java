package com.travelgo.backend.domain.attractionachievement.dto;

import com.travelgo.backend.domain.attraction.dto.AttractionDetailResponse;
import com.travelgo.backend.domain.attraction.dto.AttractionResponse;
import com.travelgo.backend.domain.attractionachievement.entity.AttractionAchievement;
import com.travelgo.backend.domain.attractionachievement.model.VisitStatus;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionAchievementResponse {

    private AttractionResponse attractionResponse;

    private VisitStatus visitStatus;

    @Builder
    public AttractionAchievementResponse(AttractionAchievement attractionAchievement) {
        this.attractionResponse = AttractionResponse.of(attractionAchievement.getAttraction());
        this.visitStatus = attractionAchievement.getVisitStatus();
    }
}
