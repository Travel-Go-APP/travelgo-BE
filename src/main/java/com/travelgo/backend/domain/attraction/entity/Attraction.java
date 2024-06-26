package com.travelgo.backend.domain.attraction.entity;

import com.travelgo.backend.domain.area.entity.Area;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attraction_id")
    private Long attractionId;

    @Enumerated(EnumType.STRING)
    private Area area;

    private boolean hiddenFlag; //히든 스테이지 설정

    @Column(name = "attraction_name", unique = true)
    private String attractionName; //이름

    private String homepage; // 홈페이지 주소

    private String address; // 주소

    private Double latitude; //위도

    private Double longitude; //경도

    @Column(length = 50000)
    private String description; //설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "path_id")
    private Path path;

    @Builder
    public Attraction(Area area, boolean hiddenFlag, String attractionName, String homepage, String address, Double latitude, Double longitude, String description) {
        this.area = area;
        this.hiddenFlag = hiddenFlag;
        this.attractionName = attractionName;
        this.homepage = homepage;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }
}
