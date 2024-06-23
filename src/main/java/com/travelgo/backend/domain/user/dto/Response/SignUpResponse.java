package com.travelgo.backend.domain.user.dto.Response;

import com.travelgo.backend.domain.user.entity.User;
import lombok.Builder;

public class SignUpResponse {
    private String email;
    private String nickname;

    @Builder
    public SignUpResponse(User user) {
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}
