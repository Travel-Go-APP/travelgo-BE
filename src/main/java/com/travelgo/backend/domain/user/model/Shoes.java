package com.travelgo.backend.domain.user.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Shoes {
    맨발("1"),
    양말("2"),
    짚신("3"),
    고무신("4"),
    슬리퍼("5"),
    구두("6"),
    운동화("7"),
    군화("8"),
    등산화("9"),
    에어조던("10");

    @JsonValue
    private final String type;
}
