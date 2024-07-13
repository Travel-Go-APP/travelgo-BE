package com.travelgo.backend.domain.item.entity;

import com.travelgo.backend.domain.area.entity.Area;
import com.travelgo.backend.domain.item.model.Grade;
import com.travelgo.backend.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "image_url")
    private String ImageUrl;

    @Column(name = "item_Rank")
    private String itemRank;

    @Enumerated(EnumType.STRING)
    private Area area;

    @Column(name = "summary")
    private String summary;

    @Column(name = "description")
    private String description;

    @Builder
    public Item(String itemName, String imageUrl, String itemRank, Area area, String summary, String description) {
        this.itemName = itemName;
        ImageUrl = imageUrl;
        this.itemRank = itemRank;
        this.area = area;
        this.summary = summary;
        this.description = description;
    }

    /**
     * 메서드
     */

    public void changeItemImage(String imageUrl) {
        this.ImageUrl = imageUrl;
    }
}
