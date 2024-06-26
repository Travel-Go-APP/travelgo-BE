package com.travelgo.backend.domain.user.entity;

import com.fasterxml.jackson.databind.BeanProperty;
import com.travelgo.backend.domain.user.dto.Request.UserRequest;
import com.travelgo.backend.domain.user.model.Bag;
import com.travelgo.backend.domain.user.model.Shoes;
import com.travelgo.backend.util.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

    @Setter
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

    private int workCount;

    private int level;

    private int quest;

    private int tg;

    @Enumerated(EnumType.STRING)
    private Shoes shoes;

    @Enumerated(EnumType.STRING)
    private Bag bag;

    @Builder
    public User(String username, String nickname, String email, String phoneNumber, double detectionRange, int experience, int workCount, int level, int quest, int tg, Shoes shoes, Bag bag) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.detectionRange = detectionRange;
        this.experience = experience;
        this.workCount = workCount;
        this.level = level;
        this.quest = quest;
        this.tg = tg;
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
        this.phoneNumber = "";
        this.detectionRange = 0;
        this.experience = 0;
        this.workCount = 0;
        this.level = 1;
        this.quest = 0;
        this.tg = 0;
        this.shoes = Shoes.맨발;
        this.bag = Bag.초급;
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

}

