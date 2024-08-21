package com.travelgo.backend.domain.event.service;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.event.dto.VisitData;
import com.travelgo.backend.domain.event.dto.VisitResult;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitCountEventService {

    // 세부 관광정보로 명소 저장
    @Transactional
    public VisitResult visitCount(String jsonData) {
        VisitResult visitResult = new VisitResult();
        VisitData SeoulCount = new VisitData(AreaCode.서울, 0.0);
        VisitData IncheonCount = new VisitData(AreaCode.인천, 0.0);
        VisitData DaejeonCount = new VisitData(AreaCode.대전, 0.0);
        VisitData DaeguCount = new VisitData(AreaCode.대구, 0.0);
        VisitData GwangjuCount = new VisitData(AreaCode.광주, 0.0);
        VisitData BusanCount = new VisitData(AreaCode.부산, 0.0);
        VisitData UlsanCount = new VisitData(AreaCode.울산, 0.0);
        VisitData SejongCount = new VisitData(AreaCode.세종특별자치시, 0.0);
        VisitData GyeonggidoCount = new VisitData(AreaCode.경기도, 0.0);
        VisitData GangwondoCount = new VisitData(AreaCode.강원특별자치도, 0.0);
        VisitData ChungcheongbukdoCount = new VisitData(AreaCode.충청북도, 0.0);
        VisitData ChungcheongnamdoCount = new VisitData(AreaCode.충청남도, 0.0);
        VisitData GyeongsangbukdoCount = new VisitData(AreaCode.경상북도, 0.0);
        VisitData GyeongsangnamdoCount = new VisitData(AreaCode.경상남도, 0.0);
        VisitData JeollabukdoCount = new VisitData(AreaCode.전북특별자치도, 0.0);
        VisitData JeollanamdoCount = new VisitData(AreaCode.전라남도, 0.0);
        VisitData JejudoCount = new VisitData(AreaCode.제주도, 0.0);

        // VisitData 객체들을 리스트에 추가
        List<VisitData> visitDataList = new ArrayList<>();
        visitDataList.add(SeoulCount);
        visitDataList.add(IncheonCount);
        visitDataList.add(DaejeonCount);
        visitDataList.add(DaeguCount);
        visitDataList.add(GwangjuCount);
        visitDataList.add(BusanCount);
        visitDataList.add(UlsanCount);
        visitDataList.add(SejongCount);
        visitDataList.add(GyeonggidoCount);
        visitDataList.add(GangwondoCount);
        visitDataList.add(ChungcheongbukdoCount);
        visitDataList.add(ChungcheongnamdoCount);
        visitDataList.add(GyeongsangbukdoCount);
        visitDataList.add(GyeongsangnamdoCount);
        visitDataList.add(JeollabukdoCount);
        visitDataList.add(JeollanamdoCount);
        visitDataList.add(JejudoCount);

        try {
            JSONArray array = getJsonArray(jsonData);
            JSONObject getObject;

            VisitData visitData = null;

            Double max = Double.MIN_VALUE;
            Double min = Double.MAX_VALUE;

            for (Object object : array) {
                getObject = (JSONObject) object;

                // 예시로 사용할 getObject
                String areaNm = (String) getObject.get("areaNm"); // getObject는 Map<String, Object> 타입이라고 가정


                switch (areaNm) {
                    case "서울특별시":
                        visitData = SeoulCount;
                        break;
                    case "인천광역시":
                        visitData = IncheonCount;
                        break;
                    case "대전광역시":
                        visitData = DaejeonCount;
                        break;
                    case "대구광역시":
                        visitData = DaeguCount;
                        break;
                    case "광주광역시":
                        visitData = GwangjuCount;
                        break;
                    case "부산광역시":
                        visitData = BusanCount;
                        break;
                    case "울산광역시":
                        visitData = UlsanCount;
                        break;
                    case "세종특별자치시":
                        visitData = SejongCount;
                        break;
                    case "경기도":
                        visitData = GyeonggidoCount;
                        break;
                    case "강원특별자치도":
                        visitData = GangwondoCount;
                        break;
                    case "충청북도":
                        visitData = ChungcheongbukdoCount;
                        break;
                    case "충청남도":
                        visitData = ChungcheongnamdoCount;
                        break;
                    case "경상북도":
                        visitData = GyeongsangbukdoCount;
                        break;
                    case "경상남도":
                        visitData = GyeongsangnamdoCount;
                        break;
                    case "전북특별자치도":
                        visitData = JeollabukdoCount;
                        break;
                    case "전라남도":
                        visitData = JeollanamdoCount;
                        break;
                    case "제주특별자치도":
                        visitData = JejudoCount;
                        break;
                    default:
                        System.out.println("해당 지역이 없습니다.");
                        break;
                }

                if (visitData != null)
                    visitData.addCount(Double.parseDouble((String) getObject.get("touNum")));
            }
            for (VisitData data : visitDataList) {
                if (data.getVisitorCount() > max) {
                    max = data.getVisitorCount();
                    visitResult.setMaxArea(data.getAreaCode());
                }
                if (data.getVisitorCount() < min) {
                    min = data.getVisitorCount();
                    visitResult.setMinArea(data.getAreaCode());
                }
            }

        } catch (CustomException e) {
            throw new CustomException(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return visitResult;
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
            throw new CustomException(ErrorCode.NOT_FOUND_ATTRACTION);

        JSONObject items = (JSONObject) parseBody.get("items");
        return (JSONArray) items.get("item");
    }
}
