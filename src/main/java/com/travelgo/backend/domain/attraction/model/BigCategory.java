package com.travelgo.backend.domain.attraction.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum BigCategory {

    자연("A01", "자연"),
    인문("A02", "인문(문화/예술/역사)"),
    레포츠("A03", "레포츠"),
    쇼핑("A04", "쇼핑"),
    음식("A05", "음식"),

    숙박("B02", "숙박"),

    코스("C01", "추천코스"),

    없음("NULL", "없음");
    ;


    private final String code;
    @JsonValue // enum 타입을 선택 가능하도록 설정
    private final String title;

    public static BigCategory getCategory(String code) {
        return Arrays.stream(BigCategory.values())
                .filter(s -> s.hasCategoryCode(code))
                .findAny()
                .orElse(null);
    }

    public boolean hasCategoryCode(String code) {
        return this.code.equals(code);
    }
}
