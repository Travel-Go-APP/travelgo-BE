package com.travelgo.backend.domain.mission.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MissionType {

    일일미션("Day"),
    주간미션("Week"),
    월간미션("Month"),
    지역미션("Local");

    @JsonValue // enum 타입을 선택 가능하도록 설정
    private final String type;
}
