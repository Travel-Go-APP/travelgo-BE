package com.travelgo.backend.domain.user.dto.Response;

import com.travelgo.backend.domain.user.model.Shoes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainPageResponse {

    private String nickname;
    private int level;
    private int experience;
    private int nextLevelExp;
    private double percentage;
    private String area; // 역지오코딩으로 호출
    private String visitingBenefit = "경험치 2배"; // 역지오코딩으로 불러온 값에 매핑할건데 임시 하드 코딩 처리
    private Shoes shoes;
    private int maxSearch; // 일일 최대 조사하기 횟수 10회
    private int possibleSearch;
    private int quest;
    private double detectionRange;
    private double experienceX;
    private double tgX;
    private int tg;
    private int workCount;



}
