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
    private Long attractionId; // DB 명소 코드

    private Long publicAttractionId; // 명소 코드

    private String attractionName; //위치 이름

    private int likes;

    private String address; // 주소

    private String city;

    private String attractionImageUrl; // 이미지


    @Builder
    public AttractionResponse(Attraction attraction) {
        this.attractionId = attraction.getAttractionId();
        this.publicAttractionId = attraction.getAttractionId();
        this.attractionName = attraction.getAttractionName();
        this.likes = attraction.getLikes();
        this.address = attraction.getAddress();
        this.city = attraction.getCity();
        this.attractionImageUrl = attraction.getAttractionImageUrl();
    }

    public static AttractionResponse of(Attraction attraction) {
        return new AttractionResponse(
                attraction.getAttractionId(),
                attraction.getAttractionId(),
                attraction.getAttractionName(),
                attraction.getLikes(),
                attraction.getAddress(),
                attraction.getCity(),
                attraction.getAttractionImageUrl()
        );
    }
}

