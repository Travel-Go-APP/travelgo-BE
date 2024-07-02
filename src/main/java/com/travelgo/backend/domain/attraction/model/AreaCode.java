package com.travelgo.backend.domain.attraction.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * {"rnum":1,"code":"1","name":"서울"}
 * {"rnum":2,"code":"2","name":"인천"}
 * {"rnum":3,"code":"3","name":"대전"}
 * {"rnum":4,"code":"4","name":"대구"}
 * {"rnum":5,"code":"5","name":"광주"}
 * {"rnum":6,"code":"6","name":"부산"}
 * {"rnum":7,"code":"7","name":"울산"}
 * {"rnum":8,"code":"8","name":"세종특별자치시"}
 * {"rnum":9,"code":"31","name":"경기도"}
 * {"rnum":10,"code":"32","name":"강원특별자치도"}
 * {"rnum":11,"code":"33","name":"충청북도"}
 * {"rnum":12,"code":"34","name":"충청남도"}
 * {"rnum":13,"code":"35","name":"경상북도"}
 * {"rnum":14,"code":"36","name":"경상남도"}
 * {"rnum":15,"code":"37","name":"전북특별자치도"}
 * {"rnum":16,"code":"38","name":"전라남도"}
 * {"rnum":17,"code":"39","name":"제주도"}
 */

@Getter
@AllArgsConstructor
public enum AreaCode {

    서울("1", "서울"),
    인천("2", "인천"),
    대전("3", "대전"),
    대구("4", "대구"),
    광주("5", "광주"),
    부산("6", "부산"),
    울산("7", "울산"),
    세종특별자치시("8", "세종특별자치시"),
    경기도("31", "경기도"),
    강원특별자치도("32", "강원특별자치도"),
    충청북도("33", "충청북도"),
    충청남도("34", "충청남도"),
    경상북도("35", "경상북도"),
    경상남도("36", "경상남도"),
    전북특별자치도("37", "전북특별자치도"),
    전라남도("38", "전라남도"),
    제주도("39", "제주도");

    private final String code;
    @JsonValue // enum 타입을 선택 가능하도록 설정
    private final String title;

    public static AreaCode getAreaCode(String code) {
        for (AreaCode areaCode : AreaCode.values()) {
            if (areaCode.getCode().equals(code)) {
                return areaCode;
            }
        }
        throw new CustomException(ErrorCode.NOT_FOUND_AREA);
    }
}
