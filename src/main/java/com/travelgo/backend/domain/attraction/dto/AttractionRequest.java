package com.travelgo.backend.domain.attraction.dto;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.attraction.model.BigCategory;
import com.travelgo.backend.domain.attraction.model.MiddleCategory;
import com.travelgo.backend.domain.attraction.model.SmallCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AttractionRequest {

    @NotNull
    private Long attractionId;

    @NotNull
    private String attractionName; //위치 이름

    @NotNull
    private String address; //주소

    @NotNull
    private Double latitude; //위도

    @NotNull
    private Double longitude; //경도

    @NotNull
    private String attractionImageUrl; // 이미지

    @NotNull
    private String description; //설명

    @Enumerated(EnumType.STRING)
    private AreaCode area;

    @Enumerated(EnumType.STRING)
    private BigCategory bigCategory;

    @Enumerated(EnumType.STRING)
    private MiddleCategory middleCategory;

    @Enumerated(EnumType.STRING)
    private SmallCategory smallCategory;

    @Builder

    public AttractionRequest(Long attractionId, String attractionName, String address, Double latitude, Double longitude, String attractionImageUrl, String description, AreaCode area, BigCategory bigCategory, MiddleCategory middleCategory, SmallCategory smallCategory) {
        this.attractionId = attractionId;
        this.attractionName = attractionName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.attractionImageUrl = attractionImageUrl;
        this.description = description;
        this.area = area;
        this.bigCategory = bigCategory;
        this.middleCategory = middleCategory;
        this.smallCategory = smallCategory;
    }
}
