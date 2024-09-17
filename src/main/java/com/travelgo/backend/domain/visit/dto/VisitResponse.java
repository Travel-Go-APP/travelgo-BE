package com.travelgo.backend.domain.visit.dto;

import com.travelgo.backend.domain.attraction.dto.AttractionCommonResponse;
import com.travelgo.backend.domain.visit.entity.Visit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VisitResponse {

    private AttractionCommonResponse attractionCommonResponse;

    private String visitTime;

    @Builder
    public VisitResponse(Visit visit) {
        this.attractionCommonResponse = AttractionCommonResponse.of(visit.getAttraction());
        this.visitTime = visit.getVisitTime();
    }

    public static VisitResponse of(Visit visit) {
        return new VisitResponse(
                AttractionCommonResponse.of(visit.getAttraction()),
                visit.getVisitTime());
    }
}
