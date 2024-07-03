package com.travelgo.backend.domain.attraction.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum MiddleCategory {

    //자연
    자연관광지("A0101", "자연관광지"),
    관광자원("A0102", "관광자원"),

    //인문
    역사관광지("A0201", "역사관광지"),
    휴양관광지("A0202", "휴양관광지"),
    체험관광지("A0203", "체험관광지"),
    산업관광지("A0204", "산업관광지"),
    건축("A0205", "건축/조형물"),
    문화시설("A0206", "문화시설"),
    축제("A0207", "축제"),
    공연행사("A0208", "공연/행사"),

    //레포츠
    레포츠소개("A0301", "레포츠소개"),
    육상레포츠("A0302", "육상 레포츠"),
    수상레포츠("A0303", "수상 레포츠"),
    항공레포츠("A0304", "항공 레포츠"),
    복합레포츠("A0305", "복합 레포츠"),

    //쇼핑
    쇼핑("A0401", "쇼핑"),

    //음식,
    음식점("A0401", "A0502"),

    //숙박
    숙박시설("B0201", "숙박시설"),

    //코스
    가족코스("C0112", "가족코스"),
    나홀로코스("C0113", "나홀로코스"),
    힐링코스("C0114", "힐링코스"),
    도보코스("C0115", "도보코스"),
    캠핑코스("C0116", "캠핑코스"),
    맛코스("C0117", "맛코스"),

    없음("NULL", "없음");
    ;

    private final String code;
    @JsonValue // enum 타입을 선택 가능하도록 설정
    private final String title;

    public static MiddleCategory getCategory(String code) {
        return Arrays.stream(MiddleCategory.values())
                .filter(s -> s.hasCategoryCode(code))
                .findAny()
                .orElse(null);
    }

    public boolean hasCategoryCode(String code) {
        return this.code.equals(code);
    }
}
