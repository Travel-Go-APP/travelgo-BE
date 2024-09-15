package com.travelgo.backend.domain.attraction.dto;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomAttractionResponse {

    private Long attractionId; // 명소 코드

    private String attractionName; //위치 이름

    private String poster; // 작성자

    private int likes; //추천수

    private String address; // 주소

    private String city;

    private String attractionImageUrl; // 이미지

    private Double latitude; //위도

    private Double longitude; // 경도


    @Builder
    public CustomAttractionResponse(Attraction attraction) {
        this.attractionId = attraction.getAttractionId();
        this.attractionName = attraction.getAttractionName();
        this.likes = attraction.getLikes();
        this.address = attraction.getAddress();
        this.city = attraction.getCity();
        this.attractionImageUrl = attraction.getAttractionImageUrl();
        this.latitude = attraction.getLatitude();
        this.longitude = attraction.getLongitude();
    }

    public static CustomAttractionResponse of(Attraction attraction) {
        return new CustomAttractionResponse(
                attraction.getAttractionId(),
                attraction.getAttractionName(),
                attraction.getPoster(),
                attraction.getLikes(),
                attraction.getAddress(),
                attraction.getCity(),
                attraction.getAttractionImageUrl(),
                attraction.getLatitude(),
                attraction.getLongitude()
        );
    }
}
