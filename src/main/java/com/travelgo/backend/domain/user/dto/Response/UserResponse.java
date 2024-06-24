package com.travelgo.backend.domain.user.dto.Response;


import com.travelgo.backend.domain.area.entity.Area;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long userId;
    private String username;
    private String nickname;
    private String email;
    private String phoneNumber;
    private double detectionRange;
    private int experience;
    private int workCount;
    private int level;
    private int quest;
    private int tg;
    private List<Attraction> attractions;

    @Getter
    @AllArgsConstructor
    public static class Attraction{
        Area area;
        boolean hiddenFlag;
        String locationName;
        String locationImage;
        Double latitude;
        Double longitude;
        String description;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateNickname {
        private String email;
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteUser {
        private String email;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Login {
        @NotNull
        private String email;
        @NotNull
        private String area;
    }
}
