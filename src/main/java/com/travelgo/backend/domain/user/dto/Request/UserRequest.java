package com.travelgo.backend.domain.user.dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequest {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUp{
        @NotNull
        private String email;

        @NotNull
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CheckNickname{
        @NotNull
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class login {
        @NotNull
        private String email;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class updateNickname{
        private String email;
        private String newNickname;
    }
}
