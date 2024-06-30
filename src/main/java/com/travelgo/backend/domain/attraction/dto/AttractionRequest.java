package com.travelgo.backend.domain.attraction.dto;

import com.travelgo.backend.domain.area.entity.AreaCode;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AttractionRequest {

    @NotNull
    private String attractionName; //위치 이름

    @NotNull
    private String homepage; // 홈페이지 주소

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

    @Builder
    public AttractionRequest(String attractionName, String homepage, String address, Double latitude, Double longitude, String attractionImageUrl, String description, AreaCode area) {
        this.attractionName = attractionName;
        this.homepage = homepage;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.attractionImageUrl = attractionImageUrl;
        this.description = description;
        this.area = area;
    }
}
