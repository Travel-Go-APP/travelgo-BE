package com.travelgo.backend.domain.weather.service;

import com.travelgo.backend.domain.weather.dto.WeatherDto;
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

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WeatherService {

    @Transactional
    public WeatherDto weatherInit(String jsonData) {
        WeatherDto weatherDto = null;
        int tmp = 0, skyCode = 0, ptyCode = 0;
        String sky = "";
        String pty = "";
        try {
            JSONArray array = getJsonArray(jsonData);
            JSONObject getObject;

            for (Object object : array) {
                getObject = (JSONObject) object;
                if (Objects.equals(getObject.get("category"), "TMP"))
                    tmp = (Integer.parseInt((String) getObject.get("fcstValue")));

                if (Objects.equals(getObject.get("category"), "SKY"))
                    skyCode = Integer.parseInt((String) getObject.get("fcstValue"));

                if (Objects.equals(getObject.get("category"), "PTY"))
                    ptyCode = Integer.parseInt((String) getObject.get("fcstValue"));
            }

            sky = switch (skyCode) {
                case 1 -> "맑음";
                case 3 -> "구름많음";
                case 4 -> "흐림";
                default -> "정보 없음";
            };

            pty = switch (ptyCode) {
                case 0 -> "없음";
                case 1 -> "비";
                case 2 -> "비/눈";
                case 3 -> "눈";
                case 4 -> "소나기";
                default -> "정보 없음";
            };

            weatherDto = WeatherDto.builder()
                    .tmp(tmp)
                    .weather(sky)
                    .pty(pty)
                    .build();

        } catch (CustomException e) {
            throw new CustomException(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherDto;
    }

    /**
     * api 데이터 파싱 메서드
     */

    private static JSONArray getJsonArray(String jsonData) throws ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
        JSONObject parseResponse = (JSONObject) jsonObject.get("response");

        JSONObject parseHeader = (JSONObject) parseResponse.get("header");

        if (!(parseHeader.get("resultCode").equals("00")))
            throw new CustomException(ErrorCode.NOT_FOUND_WEATHER);

        JSONObject parseBody = (JSONObject) parseResponse.get("body");

        JSONObject items = (JSONObject) parseBody.get("items");
        return (JSONArray) items.get("item");
    }
}
