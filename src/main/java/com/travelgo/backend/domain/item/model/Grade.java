package com.travelgo.backend.domain.item.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {

    노말("Normal"),
    레어("Rare"),
    전설("Legend"),
    스페셜("Special"),
    지역("Local");

    @JsonValue // enum 타입을 선택 가능하도록 설정
    private final String grade;
}
