package com.travelgo.backend.domain.item.dto.response;

import lombok.Getter;

@Getter
public class ShopResponse {
    private String productType;
    private String itemName;
    private String itemSummary;
    private String itemDescription;
    private Integer itemPiece;
    private Integer moneyChange;
    private Integer expChange;
    private Integer currentTg;
    private Integer currentExperience;

    // 아이템 획득
    public ShopResponse(String productType, String itemName, String itemSummary, String itemDescription, Integer itemPiece) {
        this.productType = productType;
        this.itemName = itemName;
        this.itemSummary = itemSummary;
        this.itemDescription = itemDescription;
        this.itemPiece = itemPiece;
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