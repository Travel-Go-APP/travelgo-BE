package com.travelgo.backend.domain.event.service;

import com.travelgo.backend.domain.attraction.entity.DataApiExplorer;
import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.event.dto.Period;
import com.travelgo.backend.domain.event.dto.VisitCountEventDto;
import com.travelgo.backend.domain.event.dto.VisitPercent;
import com.travelgo.backend.domain.event.entity.VisitCountBenefit;
import com.travelgo.backend.domain.event.repository.BenefitBulkRepository;
import com.travelgo.backend.domain.event.repository.VisitCountBenefitRepository;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.service.UserService;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitCountEventService {
    private final UserService userService;
    private final VisitCountBenefitRepository benefitRepository;

    @Transactional
    public VisitCountEventDto getBenefit(String email, AreaCode area) {
        User user = userService.getUser(email);
        VisitCountBenefit benefit = benefitRepository.findByAreaCode(area);
        int ranking = benefit.getRanking();
        String type = benefit.getBenefitType();
        double ratio = benefit.getBenefitRatio();

        if (type.equals("TG")) {
            user.rewardTgx(ratio);
        } else
            user.rewardExpX(ratio);

        return new VisitCountEventDto(ranking, type, ratio);
    }

    public List<VisitCountBenefit> getVisitCountData(int numOfRows, int pageNo, Period period) throws ParseException {
        String jsonData1 = DataApiExplorer.getCountInfo(numOfRows, pageNo, period.getStartDate(), period.getMiddleDate());
        String jsonData2 = DataApiExplorer.getCountInfo(numOfRows, pageNo, period.getMiddleDate(), period.getEndDate());

        // 각 지역별 방문자 수 초기화
        Map<AreaCode, Double> visitCounts1 = new HashMap<>();
        Map<AreaCode, Double> visitCounts2 = new HashMap<>();

        // 지역 코드 목록 초기화
        for (AreaCode areaCode : AreaCode.values()) {
            visitCounts1.put(areaCode, 0.0);
            visitCounts2.put(areaCode, 0.0);
        }

        JSONArray array1 = getJsonArray(jsonData1);
        JSONArray array2 = getJsonArray(jsonData2);

        // 첫 번째 JSON 배열에서 방문자 수 계산
        calculateVisitCounts(array1, visitCounts1);

        // 두 번째 JSON 배열에서 방문자 수 계산
        calculateVisitCounts(array2, visitCounts2);

        List<VisitPercent> result = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.#####");

        // 두 방문자 수의 비율 계산
        for (AreaCode areaCode : AreaCode.values()) {
            Double count1 = visitCounts1.get(areaCode);
            Double count2 = visitCounts2.get(areaCode);

            double ratio = count2 / count1;

            String formattedRatio = df.format(ratio);
            result.add(new VisitPercent(areaCode, Double.parseDouble(formattedRatio)));
        }

        List<VisitPercent> list = result.stream()
                .sorted(Comparator.comparingDouble(VisitPercent::getPercent).reversed())
                .toList();

        List<VisitCountBenefit> benefits = new ArrayList<>();

        // 보상 배수를 정의한 배열
        double[] rewardRatio = {1.3, 1.2, 1.1}; // 1등부터 3등

        for (int i = 0; i < list.size(); i++) {
            AreaCode area = list.get(i).getAreaCode();
            VisitCountBenefit benefit;

            if (i < 3) {
                benefit = VisitCountBenefit.builder()
                        .areaCode(area)
                        .ranking(i + 1)
                        .benefitType("TG")
                        .benefitRatio(rewardRatio[i])
                        .build();
            } else if (i < 6) {
                benefit = VisitCountBenefit.builder()
                        .areaCode(area)
                        .ranking(i + 1)
                        .benefitType("EXP")
                        .benefitRatio(rewardRatio[i - 3])
                        .build();
            } else {
                benefit = VisitCountBenefit.builder()
                        .areaCode(area)
                        .ranking(i + 1)
                        .benefitType("없음")
                        .benefitRatio(1.0)
                        .build();
            }

            benefits.add(benefit);
        }
        return benefits;
    }


    // 방문자 수 계산을 위한 메소드
    private void calculateVisitCounts(JSONArray array, Map<AreaCode, Double> visitCounts) {
        JSONObject getObject;
        for (Object object : array) {
            getObject = (JSONObject) object;
            String areaNm = (String) getObject.get("areaNm");

            AreaCode areaCode = getAreaCode(areaNm);
            if (areaCode != null) {
                double currentCount = visitCounts.get(areaCode);
                currentCount += Double.parseDouble((String) getObject.get("touNum"));
                visitCounts.put(areaCode, currentCount);
            } else {
                System.out.println("해당 지역이 없습니다.");
            }
        }
    }

    // 지역 이름에 해당하는 AreaCode를 반환하는 메소드
    private AreaCode getAreaCode(String areaNm) {
        return switch (areaNm) {
            case "서울특별시" -> AreaCode.서울;
            case "인천광역시" -> AreaCode.인천;
            case "대전광역시" -> AreaCode.대전;
            case "대구광역시" -> AreaCode.대구;
            case "광주광역시" -> AreaCode.광주;
            case "부산광역시" -> AreaCode.부산;
            case "울산광역시" -> AreaCode.울산;
            case "세종특별자치시" -> AreaCode.세종특별자치시;
            case "경기도" -> AreaCode.경기도;
            case "강원특별자치도" -> AreaCode.강원특별자치도;
            case "충청북도" -> AreaCode.충청북도;
            case "충청남도" -> AreaCode.충청남도;
            case "경상북도" -> AreaCode.경상북도;
            case "경상남도" -> AreaCode.경상남도;
            case "전북특별자치도", "전라북도" -> AreaCode.전북특별자치도;
            case "전라남도" -> AreaCode.전라남도;
            case "제주특별자치도" -> AreaCode.제주도;
            default -> null;
        };
    }


    private static JSONArray getJsonArray(String jsonData) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
        JSONObject parseResponse = (JSONObject) jsonObject.get("response");

        JSONObject parseHeader = (JSONObject) parseResponse.get("header");

        if (!(parseHeader.get("resultCode").equals("0000")))
            throw new CustomException(ErrorCode.UNKNOWN);

        JSONObject parseBody = (JSONObject) parseResponse.get("body");

        if ((Long) parseBody.get("totalCount") == 0)
            throw new CustomException(ErrorCode.EMPTY_VALUE);

        JSONObject items = (JSONObject) parseBody.get("items");
        return (JSONArray) items.get("item");
    }
}
