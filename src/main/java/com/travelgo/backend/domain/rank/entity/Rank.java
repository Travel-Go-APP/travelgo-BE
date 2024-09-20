package com.travelgo.backend.domain.rank.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_rank")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rankId;  // 실제 랭킹

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String nickname;  // 유저 닉네임

    @Column(nullable = false)
    private int level;  // 유저 레벨

    @Column(nullable = false)
    private int totalItem;  // 수집한 아이템 개수

    @Column(nullable = false)
    private int totalVisit;  // 방문한 지역 수집량

    @Column(nullable = false)
    private int score;  // 방문한 지역 수집량
}
