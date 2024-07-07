package com.travelgo.backend.domain.attraction.dto;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionResponse {
    private Long attractionId; // 명소 코드

    private String attractionName; //위치 이름

    private String address; // 주소

    private String attractionImageUrl; // 이미지


    @Builder
    public AttractionResponse(Attraction attraction) {
        this.attractionId = attraction.getAttractionId();
        this.attractionName = attraction.getAttractionName();
        this.address = attraction.getAddress();
        this.attractionImageUrl = attraction.getAttractionImageUrl();
    }

    public static AttractionResponse of(Attraction attraction) {
        return new AttractionResponse(
                attraction.getAttractionId(),
                attraction.getAttractionName(),
                attraction.getAddress(),
                attraction.getAttractionImageUrl()
        );
    }
}

