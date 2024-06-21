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

    private String locationName; //위치 이름

    private String locationImage; //장소 사진

    private Double latitude; //위도

    private Double longitude; //경도

    private String description; //설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "path_id")
    private Path path;

    @Builder
    public Attraction(Area area, boolean hiddenFlag, String locationName, String locationImage, Double latitude, Double longitude, String description) {
        this.area = area;
        this.hiddenFlag = hiddenFlag;
        this.locationName = locationName;
        this.locationImage = locationImage;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
    }
}
