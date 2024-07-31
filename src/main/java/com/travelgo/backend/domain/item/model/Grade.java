package com.travelgo.backend.domain.item.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.travelgo.backend.domain.area.entity.Area;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {

    일반("Normal"),
    희귀("Rare"),
    영웅("Legend"),
    전설("Special"),
    지역("Local");

    @JsonValue // enum 타입을 선택 가능하도록 설정
    private final String grade;

    public static Grade fromString(String grade){
        for(Grade a : Grade.values()){
            if(a.getGrade().equalsIgnoreCase(grade)){
                return a;
            }
        }
        throw new IllegalArgumentException("Unknown area: "+ grade);
    }
}
