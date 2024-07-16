package com.travelgo.backend.domain.item.entity;

import com.travelgo.backend.domain.area.entity.Area;
import com.travelgo.backend.domain.item.dto.request.ItemRequest;
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
    private String imageUrl;

    @Column(name = "item_rank")
    private int itemRank;

    @Enumerated(EnumType.STRING)
    private Area area;

    @Column(name = "summary")
    private String summary;

    @Column(name = "description")
    private String description;

    @Builder
    public Item(String itemName, String imageUrl, int itemRank, Area area, String summary, String description) {
        this.itemName = itemName;
        this.imageUrl = imageUrl;
        this.itemRank = itemRank;
        this.area = area;
        this.summary = summary;
        this.description = description;
    }

    /**
     * 메서드
     */

    public static Item createItem(String itemName, String imageUrl, int itemRank, Area area, String summary, String description) {
        return Item.builder()
                .itemName(itemName)
                .imageUrl(imageUrl)
                .itemRank(itemRank)
                .area(area)
                .summary(summary)
                .description(description)
                .build();
    }

    public void changeItemImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void updateItemName(String itemName){
        this.itemName = itemName;
    }

    public void updateItemRank(int itemRank){
        this.itemRank = itemRank;
    }
}
