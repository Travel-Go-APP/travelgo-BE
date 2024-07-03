package com.travelgo.backend.domain.user.dto.Request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MainPageRequest {
    private String email;
    private double latitude;
    private double longitude;

    @Builder
    public MainPageRequest(String email, double latitude, double longitude) {
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
