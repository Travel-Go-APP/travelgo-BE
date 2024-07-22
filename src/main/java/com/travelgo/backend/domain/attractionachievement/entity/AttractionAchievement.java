package com.travelgo.backend.domain.attractionachievement.entity;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttractionAchievement extends BaseTimeEntity {

    @Id
    @Column(name = "attraction_achievement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attractionAchievementId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;

    private String visitTime;

    @Builder
    public AttractionAchievement(User user, Attraction attraction) {
        this.user = user;
        this.attraction = attraction;
        this.visitTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
}
