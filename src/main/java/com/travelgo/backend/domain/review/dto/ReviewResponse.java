package com.travelgo.backend.domain.review.dto;

import com.travelgo.backend.domain.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    private Long attrationId;
    private String nickname;
    private float rating;
    private String content;
    private String date;

    @Builder
    public ReviewResponse(Review review) {
        this.attrationId = review.getAttraction().getAttractionId();
        this.nickname = review.getUser().getNickname();
        this.rating = review.getRating();
        this.content = review.getContent();
        this.date = review.getDate();
    }
}
