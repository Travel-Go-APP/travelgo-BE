package com.travelgo.backend.domain.user.entity;

import com.travelgo.backend.domain.user.dto.Request.UserRequest;
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

    @Builder
    public User(String username, String nickname, String email, String phoneNumber, double detectionRange, int experience, int workCount, int level, int quest, int tg) {
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
    }

}

