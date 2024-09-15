package com.travelgo.backend.domain.attraction.dto;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionRecordResponse {
    private Long attractionId; // DB 명소 코드

    private Double latitude; //위도

    private Double longitude; //경도


    @Builder
    public AttractionRecordResponse(Attraction attraction) {
        this.attractionId = attraction.getAttractionId();
        this.latitude = attraction.getLatitude();
        this.longitude = attraction.getLongitude();
    }

    public static AttractionRecordResponse of(Attraction attraction) {
        return new AttractionRecordResponse(
                attraction.getAttractionId(),
                attraction.getLatitude(),
                attraction.getLongitude()
        );
    }
}

