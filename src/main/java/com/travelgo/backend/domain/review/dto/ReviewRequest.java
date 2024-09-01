package com.travelgo.backend.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    @NotNull
    private Long attractionId;

    @NotNull
    private String email;

    @NotNull
    private String content;

    @NotNull
    private float rating;

    @Builder
    public ReviewRequest(Long attractionId, String content, float rating) {
        this.attractionId = attractionId;
        this.content = content;
        this.rating = rating;
    }
}
