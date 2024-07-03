package com.travelgo.backend.domain.attraction.entity;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.attraction.model.BigCategory;
import com.travelgo.backend.domain.attraction.model.MiddleCategory;
import com.travelgo.backend.domain.attraction.model.SmallCategory;
import com.travelgo.backend.domain.path.entity.Path;
import com.travelgo.backend.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attraction extends BaseTimeEntity {

    @Id
    @Column(name = "attraction_id")
    private Long attractionId;

    @Enumerated(EnumType.STRING)
    private AreaCode area;

    @Enumerated(EnumType.STRING)
    private BigCategory bigCategory;

    @Enumerated(EnumType.STRING)
    private MiddleCategory middleCategory;

    @Enumerated(EnumType.STRING)
    private SmallCategory smallCategory;

    private boolean hiddenFlag; //히든 스테이지 설정

    @Column(name = "attraction_name", unique = true)
    private String attractionName; //이름

    private String address; // 주소

    private Double latitude; //위도

    private Double longitude; //경도

    private String attractionImageUrl; // 사진 URL

    @Column(columnDefinition = "TEXT")
    private String description; //설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "path_id")
    private Path path;

    @Builder

    public Attraction(Long attractionId, AreaCode area, BigCategory bigCategory, MiddleCategory middleCategory, SmallCategory smallCategory, boolean hiddenFlag, String attractionName, String address, Double latitude, Double longitude, String attractionImageUrl, String description, Path path) {
        this.attractionId = attractionId;
        this.area = area;
        this.bigCategory = bigCategory;
        this.middleCategory = middleCategory;
        this.smallCategory = smallCategory;
        this.hiddenFlag = hiddenFlag;
        this.attractionName = attractionName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.attractionImageUrl = attractionImageUrl;
        this.description = description;
        this.path = path;
    }
}
