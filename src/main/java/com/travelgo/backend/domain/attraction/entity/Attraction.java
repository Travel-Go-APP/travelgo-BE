package com.travelgo.backend.domain.attraction.entity;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.attraction.model.BigCategory;
import com.travelgo.backend.domain.attraction.model.MiddleCategory;
import com.travelgo.backend.domain.attraction.model.SmallCategory;
import com.travelgo.backend.domain.path.entity.Path;
import com.travelgo.backend.domain.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attraction extends BaseTimeEntity {
    @Id
    @Column(name = "attraction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attractionId;

    private Long publicAttractionId;

    @Enumerated(EnumType.STRING)
    private AreaCode area;

    @Enumerated(EnumType.STRING)
    private BigCategory bigCategory;

    @Enumerated(EnumType.STRING)
    private MiddleCategory middleCategory;

    @Enumerated(EnumType.STRING)
    private SmallCategory smallCategory;

    private int likes;

    private String poster; // 등록자 이름

    private Boolean customFlag;

    @Column(name = "attraction_name", unique = true)
    private String attractionName; //이름

    private String address; // 주소

    private String city; // 시

    private Double latitude; //위도

    private Double longitude; //경도

    private String attractionImageUrl; // 사진 URL

    @Column(columnDefinition = "TEXT")
    private String description; //설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "path_id")
    private Path path;

    @Builder
    public Attraction(Long publicAttractionId, AreaCode area, BigCategory bigCategory, MiddleCategory middleCategory, SmallCategory smallCategory, boolean customFlag, int likes, String poster, String attractionName, String address, String city, Double latitude, Double longitude, String attractionImageUrl, String description) {
        this.publicAttractionId = publicAttractionId;
        this.area = area;
        this.bigCategory = bigCategory;
        this.middleCategory = middleCategory;
        this.smallCategory = smallCategory;
        this.customFlag = customFlag;
        this.likes = likes;
        this.poster = poster;
        this.attractionName = attractionName;
        this.address = address;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.attractionImageUrl = attractionImageUrl;
        this.description = description;
    }

    public void plusLikes() {
        likes += 1;
    }

    public void minusLikes() {
        likes -= 1;
    }

    public void initialAttractionImageUrl(String attractionImageUrl) {
        this.attractionImageUrl = attractionImageUrl;
    }
}
