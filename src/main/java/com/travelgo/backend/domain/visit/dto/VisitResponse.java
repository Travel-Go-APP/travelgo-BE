package com.travelgo.backend.domain.visit.dto;

import com.travelgo.backend.domain.attraction.dto.AttractionRecordResponse;
import com.travelgo.backend.domain.visit.entity.Visit;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VisitResponse {

    private AttractionRecordResponse attractionRecordResponse;

    private String visitTime;

    @Builder
    public VisitResponse(Visit visit) {
        this.attractionRecordResponse = AttractionRecordResponse.of(visit.getAttraction());
        this.visitTime = visit.getVisitTime();
    }

    public static VisitResponse of(Visit visit) {
        return new VisitResponse(
                AttractionRecordResponse.of(visit.getAttraction()),
                visit.getVisitTime());
    }
}
