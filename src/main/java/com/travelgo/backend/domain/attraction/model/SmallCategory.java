package com.travelgo.backend.domain.attraction.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum SmallCategory {
    // 자연관광지
    국립공원("A01010100", "국립공원"),
    도립공원("A01010200", "도립공원"),
    군립공원("A01010300", "군립공원"),
    산("A01010400", "산"),
    자연생태관광지("A01010500", "자연생태관광지"),
    자연휴양림("A01010600", "자연휴양림"),
    수목원("A01010700", "수목원"),
    폭포("A01010800", "폭포"),
    계곡("A01010900", "계곡"),
    약수터("A01011000", "약수터"),
    해안절경("A01011100", "해안절경"),
    해수욕장("A01011200", "해수욕장"),
    섬("A01011300", "섬"),
    항구포구("A01011400", "항구/포구"),
    등대("A01011600", "등대"),
    호수("A01011700", "호수"),
    강("A01011800", "강"),
    동굴("A01011900", "동굴"),

    // 관광자원
    희귀동식물("A01020100", "희귀동.식물"),
    기암괴석("A01020200", "기암괴석"),

    // 역사 관광지
    고궁("A02010100", "고궁"),
    성("A02010200", "성"),
    문("A02010300", "문"),
    고택("A02010400", "고택"),
    생가("A02010500", "생가"),
    민속마을("A02010600", "민속마을"),
    유적지사적지("A02010700", "유적지/사적지"),
    사찰("A02010800", "사찰"),
    종교성지("A02010900", "종교성지"),
    안보관광("A02011000", "안보관광"),

    //휴양관광지
    관광단지("A02020200", "관광단지"),
//    온천욕장스파("A02020300", "온천/욕장/스파"),
//    이색찜질방("A02020400", "이색찜질방"),
//    헬스투어("A02020500", "헬스투어"),
    테마공원("A02020600", "테마공원"),
    공원("A02020700", "공원"),
    유람선잠수함("A02020800", "유람/잠수함관광"),

    없음("NULL", "없음");

    private final String code;
    @JsonValue // enum 타입을 선택 가능하도록 설정
    private final String title;

    public static SmallCategory getCategory(String code) {
        return Arrays.stream(SmallCategory.values())
                .filter(s -> s.hasCategoryCode(code))
                .findAny()
                .orElse(null);
    }

    public boolean hasCategoryCode(String code) {
        return this.code.equals(code);
    }
}