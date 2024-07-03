package com.travelgo.backend.domain.user.dto.Request;

import com.travelgo.backend.domain.user.model.Shoes;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequest {

    @NotNull
    private String email;

    @NotNull
    private String nickname;

    @Builder
    public SignUpRequest(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateExp{
        private String email;
        private int experience;
    }
}
