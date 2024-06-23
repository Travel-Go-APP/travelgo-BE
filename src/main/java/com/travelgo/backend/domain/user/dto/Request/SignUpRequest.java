package com.travelgo.backend.domain.user.dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
