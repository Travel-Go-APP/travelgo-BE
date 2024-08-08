package com.travelgo.backend.domain.area.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Area {
    서울("Seoul"),
    부산("Busan"),
    대구("Daegu"),
    인천("Incheon"),
    광주("Gwangju"),
    대전("Daejeon"),
    울산("Ulsan"),
    세종특별자치시("Sejong"),
    경기("Gyeonggido"),
    강원특별자치도("Gangwondo"),
    충남("Chungcheongnamdo"),
    충북("Chungcheongbukdo"),
    전남("Jeollanamdo"),
    전북("Jeollabukdo"),
    경남("Gyeonsangnamdo"),
    경북("Gyeonsangbukdo"),
    제주특별자치도("Jejudo");
//    기본("Common");

    @JsonValue // enum 타입을 선택 가능하도록 설정
    private final String value;

    public static Area fromString(String area){
        for(Area a : Area.values()){
            if(a.getValue().equalsIgnoreCase(area)){
                return a;
            }
        }
        throw new IllegalArgumentException("Unknown area: "+ area);
    }
}

