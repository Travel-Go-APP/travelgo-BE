package com.travelgo.backend.domain.attractionachievement.dto;

import com.travelgo.backend.global.validator.customValid.EmailValid;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionAchievementRequest {

    @NotNull
    @EmailValid
    @Schema(description = "이메일")
    private String email;

    @NotNull
    @Schema(description = "명소 Id")
    private Long attractionId; // 명소 Id
}

