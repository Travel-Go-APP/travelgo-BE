package com.travelgo.backend.domain.attraction.dto;

import com.travelgo.backend.domain.area.entity.Area;
import com.travelgo.backend.domain.attraction.entity.Attraction;
import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;

@Data
public class AttractionResponse {
    private String attractionName; //위치 이름

    private String homepage; // 홈페이지 주소

    private String address; // 주소

    private Double latitude; //위도

    private Double longitude; //경도

    private String description; //설명

    private Area area;

    @Builder
    public AttractionResponse(Attraction attraction) {
        this.attractionName = attraction.getAttractionName();
        this.address = attraction.getAddress();
        this.homepage = attraction.getHomepage();
        this.latitude = attraction.getLatitude();
        this.longitude = attraction.getLongitude();
        this.description = attraction.getDescription();
        this.area = attraction.getArea();
    }
}
