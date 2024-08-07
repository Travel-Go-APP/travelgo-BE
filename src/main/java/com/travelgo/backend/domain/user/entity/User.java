package com.travelgo.backend.domain.user.entity;

import com.travelgo.backend.domain.user.dto.Request.UserEditRequest;
import com.travelgo.backend.domain.user.dto.Request.UserRequest;
import com.travelgo.backend.domain.user.model.Bag;
import com.travelgo.backend.domain.user.model.Shoes;
import com.travelgo.backend.util.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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

//    @Column(name = "phone_number", unique = true)
//    private String phoneNumber;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Column(name = "detection_range")
    @NotNull
    private double detectionRange;

    private int experience;

    private int nextLevelExp;

    private double percentage;

    private int workCount;

    private int level;

    private int quest;

    private int tg;

    private int maxSearch;
    private int possibleSearch;
    private double experienceX;
    private double tgX;

    @Enumerated(EnumType.STRING)
    private Shoes shoes;

    @Enumerated(EnumType.STRING)
    private Bag bag;

    @Builder
    public User(String username, String nickname, String email, String phoneNumber,Role role, double detectionRange, int experience, int nextLevelExp, double percentage, int workCount, int level, int quest, int tg, int maxSearch, int possibleSearch, double experienceX, double tgX, Shoes shoes, Bag bag) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
//        this.phoneNumber = phoneNumber;
        this.role = role;
        this.detectionRange = detectionRange;
        this.experience = experience;
        this.nextLevelExp = nextLevelExp;
        this.percentage = percentage;
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

    public static User createUser(UserRequest.SignUp request){
        User user = new User();
        user.signupUser(request);
        return user;
    }

    public void signupUser(UserRequest.SignUp request){
        this.email = request.getEmail();
        this.nickname = request.getNickname();
        this.username = "";
//        this.phoneNumber = "";
        this.detectionRange = 0;
        this.experience = 0;
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

    public void addExperience(int exp){
        this.experience += exp;
    }

    public void levelUp(){
        this.level++;
    }

    public void reduceExperience(int exp){
        this.experience -= exp;
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

//    public void updateUser(UserEditRequest request) {
//        this.name = request.name();
//        this.phoneNumber = request.phoneNumber();
//    }

    public void changeAdmin(){
        this.role = Role.ADMIN;
    }
}

