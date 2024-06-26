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
    @NotNull
    private String shoes;

    @Builder

    public SignUpRequest(String email, String nickname, String shoes) {
        this.email = email;
        this.nickname = nickname;
        this.shoes = shoes;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateExp{
        private String email;
        private int experience;
    }
}
