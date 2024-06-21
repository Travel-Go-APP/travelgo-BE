package com.travelgo.backend.domain.user.entity;

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
}

