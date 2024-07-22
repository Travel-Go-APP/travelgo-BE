package com.travelgo.backend.domain.attraction.dto;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import com.travelgo.backend.domain.attraction.model.AreaCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDetailResponse {
    private Long attractionId;

    private String attractionName; //위치 이름

    private String address; // 주소

    private String city;

    private Double latitude; //위도

    private Double longitude; //경도

    private String attractionImageUrl; // 이미지

    private String description; //설명

    private AreaCode area;

    @Builder
    public AttractionDetailResponse(Attraction attraction) {
        this.attractionId = attraction.getAttractionId();
        this.attractionName = attraction.getAttractionName();
        this.address = attraction.getAddress();
        this.city = attraction.getCity();
        this.latitude = attraction.getLatitude();
        this.longitude = attraction.getLongitude();
        this.attractionImageUrl = attraction.getAttractionImageUrl();
        this.description = attraction.getDescription();
        this.area = attraction.getArea();
    }

    public static AttractionDetailResponse of(Attraction attraction) {
        return new AttractionDetailResponse(
                attraction.getAttractionId(),
                attraction.getAttractionName(),
                attraction.getAddress(),
                attraction.getCity(),
                attraction.getLatitude(),
                attraction.getLongitude(),
                attraction.getAttractionImageUrl(),
                attraction.getDescription(),
                attraction.getArea()
        );
    }
}
