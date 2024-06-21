package com.travelgo.backend.domain.review.entity;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.util.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attraction_id")
    private Attraction attraction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Max(value = 5) @Min(value = 0)
    private Float rating;

    private String content;

    private LocalDateTime time;

    @Column(name = "review_image_url")
    private String reviewImageUrl;

    @Builder
    public Review(Attraction attraction, User user, Float rating, String content, LocalDateTime time, String reviewImageUrl) {
        this.attraction = attraction;
        this.user = user;
        this.rating = rating;
        this.content = content;
        this.time = time;
        this.reviewImageUrl = reviewImageUrl;
    }
}
