package com.travelgo.backend.domain.item.dto.response;

import lombok.Getter;

@Getter
public class ShopResponse {
    private String productType;
    private String itemName;
    private String itemSummary;
    private String itemDescription;
    private Integer itemPiece;
    private Integer tg;
    private Integer tgChange;
    private Integer moneyChange;
    private Integer expChange;
    private Integer currentTg;
    private Integer currentExperience;
    private Integer level;
    private Integer nextLevelExp;
    private Double experiencePercent;

    // 아이템 획득
    public ShopResponse(String productType,Integer currentTg, String itemName, String itemSummary, String itemDescription, Integer itemPiece) {
        this.productType = productType;
        this.currentTg = currentTg;
        this.itemName = itemName;
        this.itemSummary = itemSummary;
        this.itemDescription = itemDescription;
        this.itemPiece = itemPiece;
    }

    // 돈 리스폰스
    public ShopResponse(String productType, Integer currentTg, Integer currentExperience, Integer moneyChange, Integer expChange) {
        this.productType = productType;
        this.currentTg = currentTg;
        this.currentExperience = currentExperience;
        this.moneyChange = moneyChange;
        this.expChange = expChange;
    }

    // 경험치 리스폰스
    public ShopResponse(String productType, Integer currentTg, Integer currentExperience, Integer moneyChange, Integer expChange, Integer level, Integer nextLevelExp, Double experiencePercent) {
        this.productType = productType;
        this.currentTg = currentTg;
        this.currentExperience = currentExperience;
        this.moneyChange = moneyChange;
        this.expChange = expChange;
        this.level = level;
        this.nextLevelExp = nextLevelExp;
        this.experiencePercent = experiencePercent;
    }

    // TG 보상 설정
    public void setTgChange(Integer tgChange) {
        this.tgChange = tgChange;
    }

    // 경험치 및 관련 정보 설정
    public void setExpChange(Integer expChange) {
        this.expChange = expChange;
    }

    public void setCurrentExperience(Integer currentExperience) {
        this.currentExperience = currentExperience;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setNextLevelExp(Integer nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    public void setExperiencePercent(Double experiencePercent) {
        this.experiencePercent = experiencePercent;
    }
}