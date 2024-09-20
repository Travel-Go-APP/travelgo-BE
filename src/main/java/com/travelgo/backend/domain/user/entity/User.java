package com.travelgo.backend.domain.user.entity;

import com.travelgo.backend.domain.user.dto.AgreeDto;
import com.travelgo.backend.domain.user.dto.Request.UserRequest;
import com.travelgo.backend.domain.user.model.Bag;
import com.travelgo.backend.domain.user.model.Shoes;
import com.travelgo.backend.domain.userItems.entity.UserItems;
import com.travelgo.backend.domain.common.entity.BaseTimeEntity;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name")
    @NotNull
    private String username;

    @Column(unique = true)
    @NotNull
    private String nickname;

    @Column(nullable = false, length = 50, unique = true)
    @Email
    private String email; // kakaoId -> email

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "detection_range")
    @NotNull
    private double detectionRange;
    private int experience;
    private int nextLevelExp;
    private double percentage;
    private int reportCount;
    private int workCount;
    private int level;
    private int quest;
    private int tg;
    private int maxSearch;
    private int possibleSearch;
    private double experienceX;
    private double tgX;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "agree_id")
    private UserAgree userAgree;

    @Enumerated(EnumType.STRING)
    private Shoes shoes;

    @Enumerated(EnumType.STRING)
    private Bag bag;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserItems> userItems = new ArrayList<>();

    @Builder
    public User(String username, String nickname, String email, String phoneNumber, double detectionRange, int experience, int nextLevelExp, double percentage, int reportCount, int workCount, int level, int quest, int tg, int maxSearch, int possibleSearch, double experienceX, double tgX, Shoes shoes, Bag bag) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.detectionRange = detectionRange;
        this.experience = experience;
        this.nextLevelExp = nextLevelExp;
        this.percentage = percentage;
        this.reportCount = reportCount;
        this.workCount = workCount;
        this.level = level;
        this.quest = quest;
        this.tg = tg;
        this.maxSearch = maxSearch;
        this.possibleSearch = possibleSearch;
        this.experienceX = experienceX;
        this.tgX = tgX;
        this.shoes = shoes;
        this.bag = bag;
    }

    public static User createUser(UserRequest.SignUp request) {
        User user = new User();
        user.signupUser(request);
        return user;
    }

    public void signupUser(UserRequest.SignUp request) {
        this.email = request.getEmail();
        this.nickname = request.getNickname();
        this.username = "";
        this.phoneNumber = "";
        this.detectionRange = 0;
        this.experience = 0;
        this.reportCount = 0;
        this.workCount = 0;
        this.level = 1;
        this.quest = 0;
        this.tg = 0;
        this.shoes = Shoes.맨발;
        this.bag = Bag.초급;
        this.maxSearch = 10;
        this.possibleSearch = 10;
        this.experienceX = 1.0;
        this.tgX = 1.0;
    }

    public void addExperience(int exp) {
        int adjustedExp = (int) (exp * this.experienceX); //배율 설정

        this.experience += adjustedExp;
        checkLevelUp();
    }

    private void checkLevelUp() {
        int[] expTable = UserExp.getExpTable();
        while (this.experience >= expTable[this.level]) {
            reduceExperience(expTable[this.level]);
            levelUp();
        }
    }

    public void levelUp() {
        this.level++;
    }

    public void addTg(int amount) {
        int adjustedAmount = (int) (amount * this.tgX); //배율 설정
        if (this.tg + amount < 0) {
            // 현재 가진 TG보다 더 큰 금액을 차감하려 할 경우, TG를 0으로 설정
            this.tg = 0;
        } else {
            this.tg += adjustedAmount;
        }
    }

    public void loseTgPercentage(int percentage) {
        int tgLost = this.tg * percentage / 100;
        this.addTg(-tgLost);  // addTg로 유효성 검사와 함께 TG 차감
    }

    public void recoverPossibleSearch(int amount) {

        this.possibleSearch = Math.min(this.possibleSearch + amount, this.maxSearch);
    }

    public void decreasePossibleSearch(int amount) {
        if (this.possibleSearch - amount < 0) {
            this.possibleSearch = 0; // 최소 0 이하로는 내려가지 않도록 함
        } else {
            this.possibleSearch -= amount;
        }
    }

    public void reduceExperience(int exp) {
        this.experience -= exp;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void rewardTgx(double ratio) {
        this.tgX = ratio;
    }

    public void rewardExpX(double ratio) {
        this.experienceX = ratio;
    }

    public void saveAgree(AgreeDto agree) {
        this.userAgree = new UserAgree(agree);
    }

    public void report() {
        reportCount += 1;
    }

}

