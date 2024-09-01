package com.travelgo.backend.domain.item.dto.response;

import com.travelgo.backend.domain.item.entity.Item;
import lombok.Getter;

@Getter
public class ShopResponse {
    private String productType;
    private String itemName;
    private String itemSummary;
    private String itemDescription;
    private Integer moneyChange;
    private Integer expChange;
    private Integer currentTg;
    private Integer currentExperience;

    // 아이템 획득
    public ShopResponse(Item item) {
        this.productType = "item";
        this.itemName = item.getItemName();
        this.itemSummary = item.getSummary();
        this.itemDescription = item.getDescription();
    }

    // 돈 또는 경험치 획득
    public ShopResponse(String productType, Integer currentTg, Integer currentExperience, Integer moneyChange, Integer expChange) {
        this.productType = productType;
        this.currentTg = currentTg;
        this.currentExperience = currentExperience;
        this.moneyChange = moneyChange;
        this.expChange = expChange;
    }
}
