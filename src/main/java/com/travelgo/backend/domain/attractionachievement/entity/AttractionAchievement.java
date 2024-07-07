package com.travelgo.backend.domain.attractionachievement.entity;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import com.travelgo.backend.domain.attractionachievement.model.VisitStatus;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.util.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @Enumerated(EnumType.STRING)
    private VisitStatus visitStatus;

    @Builder
    public AttractionAchievement(User user, Attraction attraction, VisitStatus visitStatus) {
        this.user = user;
        this.attraction = attraction;
        this.visitStatus = visitStatus;
    }
}
