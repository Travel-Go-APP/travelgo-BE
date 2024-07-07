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
public class AttractionResponse {
    private Long attractionId;

    private String attractionName; //위치 이름

    private String address; // 주소

    private Double latitude; //위도

    private Double longitude; //경도

    private String attractionImageUrl; // 이미지

    private String description; //설명

    private AreaCode area;

//    private BigCategory bigCategory; //대분류
//
//    private MiddleCategory middleCategory; //중분류
//
//    private SmallCategory smallCategory; //소분류

    @Builder
    public AttractionResponse(Attraction attraction) {
        this.attractionId = attraction.getAttractionId();
        this.attractionName = attraction.getAttractionName();
        this.address = attraction.getAddress();
        this.latitude = attraction.getLatitude();
        this.longitude = attraction.getLongitude();
        this.attractionImageUrl = attraction.getAttractionImageUrl();
        this.description = attraction.getDescription();
        this.area = attraction.getArea();
//        this.bigCategory = attraction.getBigCategory();
//        this.middleCategory = attraction.getMiddleCategory();
//        this.smallCategory = attraction.getSmallCategory();
    }

    public static AttractionResponse of(Attraction attraction) {
        return new AttractionResponse(
                attraction.getAttractionId(),
                attraction.getAttractionName(),
                attraction.getAddress(),
                attraction.getLatitude(),
                attraction.getLongitude(),
                attraction.getAttractionImageUrl(),
                attraction.getDescription(),
                attraction.getArea()
        );
//        attraction.getBigCategory();
//        attraction.getMiddleCategory();
//        attraction.getSmallCategory();
    }
}
