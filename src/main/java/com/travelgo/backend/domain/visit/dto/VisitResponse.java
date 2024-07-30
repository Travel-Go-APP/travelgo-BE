package com.travelgo.backend.domain.visit.dto;

import com.travelgo.backend.domain.attraction.dto.AttractionResponse;
import com.travelgo.backend.domain.visit.entity.AttractionAchievement;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionAchievementResponse {

    private AttractionResponse attractionResponse;

    private String visitTime;

    @Builder
    public AttractionAchievementResponse(AttractionAchievement attractionAchievement) {
        this.attractionResponse = AttractionResponse.of(attractionAchievement.getAttraction());
        this.visitTime = attractionAchievement.getVisitTime();
    }

    public static AttractionAchievementResponse of(AttractionAchievement attractionAchievement) {
        return new AttractionAchievementResponse(
                AttractionResponse.of(attractionAchievement.getAttraction()),
                attractionAchievement.getVisitTime());
    }
}
