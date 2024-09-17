package com.travelgo.backend.domain.attraction.dto;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionCommonResponse {
    private Long attractionId; // DB 명소 코드

    private String attractionName; //위치 이름

    private String address; // 주소

    private String city;

    private String attractionImageUrl; // 이미지


    @Builder
    public AttractionCommonResponse(Attraction attraction) {
        this.attractionId = attraction.getAttractionId();
        this.attractionName = attraction.getAttractionName();
        this.address = attraction.getAddress();
        this.city = attraction.getCity();
        this.attractionImageUrl = attraction.getAttractionImageUrl();
    }

    public static AttractionCommonResponse of(Attraction attraction) {
        return new AttractionCommonResponse(
                attraction.getAttractionId(),
                attraction.getAttractionName(),
                attraction.getAddress(),
                attraction.getCity(),
                attraction.getAttractionImageUrl()
        );
    }
}

