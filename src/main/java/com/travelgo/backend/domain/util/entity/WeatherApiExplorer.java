package com.travelgo.backend.domain.util.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class WeatherApiExplorer {

    public static final String SERVICE_KEY = "=qQGmrHZLWFbuo8cT4CGtZpuYOKZDJBHtoMygLD6eC4F8erExrBQEUWda/Z3kNQXSeLNd/Nc1nM6/AYiNTJD47w==";

    public static HttpURLConnection getWeather(int numOfRows, int pageNo, String date, String time, int nx, int ny) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + SERVICE_KEY);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(numOfRows), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(pageNo), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("dataType", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("JSON", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("base_date", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(date, StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("base_time", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(time, StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("nx", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(nx), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("ny", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(ny), StandardCharsets.UTF_8));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        return conn;
    }

    public static String getWeatherInfo(int numOfRows, int pageNo, String date, String time, int nx, int ny) {
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection conn = WeatherApiExplorer.getWeather(numOfRows, pageNo, date, time, nx, ny);

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
}
