package com.travelgo.backend.domain.user.dto.Response;

import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.entity.UserExp;
import lombok.Getter;

import java.util.List;


@Getter
public class UserSearchResponse {
    private Long userId;
    private String email;
    private int tg;
    private String result;
    private int eventCategory;
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

    private List<String> merchantResults;  // 상인 이벤트 결과 리스트 추가

    public UserSearchResponse(User user, int eventCategory) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
        this.tg = user.getTg();
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
    }

    public UserSearchResponse(User user, int eventCategory,  Integer tgChange, Integer expChange, Integer possibleSearchChange) {
        this(user, eventCategory);

        this.tgChange = tgChange;
        this.expChange = expChange;
        this.possibleSearchChange = possibleSearchChange;
    }

    public UserSearchResponse(User user, int eventCategory, List<String> merchantResults) {

        this(user, eventCategory);
        this.merchantResults = merchantResults;
    }

    public UserSearchResponse(User user, int eventCategory,  Integer tgChange) {
        this(user, eventCategory);
        this.tgChange = tgChange;
    }
}