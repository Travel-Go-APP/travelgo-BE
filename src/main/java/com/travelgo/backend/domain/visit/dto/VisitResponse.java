package com.travelgo.backend.domain.visit.dto;

import com.travelgo.backend.domain.attraction.dto.AttractionResponse;
import com.travelgo.backend.domain.visit.entity.Visit;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VisitResponse {

    private AttractionResponse attractionResponse;

    private String visitTime;

    @Builder
    public VisitResponse(Visit visit) {
        this.attractionResponse = AttractionResponse.of(visit.getAttraction());
        this.visitTime = visit.getVisitTime();
    }

    public static VisitResponse of(Visit visit) {
        return new VisitResponse(
                AttractionResponse.of(visit.getAttraction()),
                visit.getVisitTime());
    }
}
