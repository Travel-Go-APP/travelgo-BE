package com.travelgo.backend.domain.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDto {
    private int tmp; // 온도
    private String weather; // 날씨
    private String pty; // 강수
}
