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

    private int price;

    @Column(name = "item_type")
    private String itemType;

    @Column(name = "item_image_url")
    private String itemImageUrl;

    @Column(name = "item_description")
    private String itemDescription;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Enumerated(EnumType.STRING)
    private Area area;

    @Builder
    public Item(String itemName, int price, String itemType, String itemImageUrl, String itemDescription, Grade grade, Area area) {
        this.itemName = itemName;
        this.price = price;
        this.itemType = itemType;
        this.itemImageUrl = itemImageUrl;
        this.itemDescription = itemDescription;
        this.grade = grade;
        this.area = area;
    }

    /**
     * 메서드
     */

    public void changeItemImage(String imageUrl) {
        this.itemImageUrl = imageUrl;
    }
}
