package com.travelgo.backend.domain.util.entity;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Slf4j
public class WeatherApiExplorer {

    public static final String SERVICE_KEY = "=qQGmrHZLWFbuo8cT4CGtZpuYOKZDJBHtoMygLD6eC4F8erExrBQEUWda/Z3kNQXSeLNd/Nc1nM6/AYiNTJD47w==";

    public static HttpURLConnection getWeather(double latitude, double longitude) throws IOException {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        String date = getDate(0);
        String time = getTime();
        if (time.equals("-100")) {
            time = "2300";
            date = getDate(1);
        }
        log.info("{}", date);
        log.info("{}", time);

        LatXLngY tmp = LatXLngY.convertGRID_GPS(0, latitude, longitude);

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + SERVICE_KEY);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("12", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("1", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("dataType", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("JSON", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("base_date", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(date, StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("base_time", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(time, StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("nx", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf((int) tmp.getX()), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("ny", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf((int) tmp.getY()), StandardCharsets.UTF_8));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        return conn;
    }

    public static String getWeatherInfo(double latitude, double longitude) {
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection conn = WeatherApiExplorer.getWeather(latitude, longitude);

            BufferedReader br = WeatherApiExplorer.getResponseCode(conn); // 상태코드 반환

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static BufferedReader getResponseCode(HttpURLConnection conn) throws IOException {
        BufferedReader br;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        return br;
    }


    public static String getDate(int minus) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        // 현재 날짜 가져오기
        LocalDate now = LocalDate.now().minusDays(minus);

        // DateTimeFormatter를 사용하여 날짜 포맷 변경
        return now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String getTime() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        // 현재 시간 가져오기
        LocalDateTime now = LocalDateTime.now();

        // DateTimeFormatter를 사용하여 시간 포맷 변경
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        String formattedTime = now.format(formatter);

        return convertTime(formattedTime);
    }

    public static String convertTime(String inputTime) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        int[] arr = {23, 20, 17, 14, 11, 8, 5, 2, -1};
        int inputHour = Integer.parseInt(inputTime.substring(0, 2));
        int inputMinute = Integer.parseInt(inputTime.substring(2));

        boolean refresh = false;

        if (inputMinute > 10) {
            refresh = true;
        }

        for (int i = 0; i < arr.length; i++) {
            if ((!refresh && arr[i] < inputHour) || (refresh && arr[i] <= inputHour)) {
                inputHour = arr[i];
                break;
            }
        }

        String convertedHourStr = String.format("%02d", inputHour);
        return convertedHourStr + "00";
    }
}
