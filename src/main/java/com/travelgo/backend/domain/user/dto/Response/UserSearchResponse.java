package com.travelgo.backend.domain.user.dto.Response;

import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.entity.UserExp;
import lombok.Getter;

@Getter
public class UserSearchResponse {
    private Long userId;
    private String email;
    private int tg;
    private String result;
    private String eventCategory;
    private String nickname;
    private int level;
    private int experience;
    private int nextLevelExp;
    private double percentage;
    private String visitingBenefit;
    private int maxSearch;
    private int possibleSearch;
    private int quest;
    private double detectionRange;
    private double experienceX;
    private double tgX;
    private int workCount;

    private Integer tgChange;
    private Integer expChange;
    private Integer possibleSearchChange;

    public UserSearchResponse(User user, String eventCategory, String result,  Integer tgChange, Integer expChange, Integer possibleSearchChange) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.tg = user.getTg();
        this.result = result;
        this.eventCategory = eventCategory;

        this.nickname = user.getNickname();
        this.level = user.getLevel();
        this.experience = user.getExperience();
        this.nextLevelExp = UserExp.getExpTable()[user.getLevel()];
        this.percentage = (double) user.getExperience() / this.nextLevelExp * 100;
        this.visitingBenefit = "경험치 2배"; // 임시 하드코딩
        this.maxSearch = user.getMaxSearch();
        this.possibleSearch = user.getPossibleSearch();
        this.quest = user.getQuest();
        this.detectionRange = user.getDetectionRange();
        this.experienceX = user.getExperienceX();
        this.tgX = user.getTgX();
        this.workCount = user.getWorkCount();

        this.tgChange = tgChange;
        this.expChange = expChange;
        this.possibleSearchChange = possibleSearchChange;
    }
}