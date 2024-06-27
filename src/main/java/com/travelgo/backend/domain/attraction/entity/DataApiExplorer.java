package com.travelgo.backend.domain.attraction.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * <common>
 * numOfRows : 한페이지 결과 수
 * pageNo : 페이지 번
 * MobileOS : IOS (아이폰), AND (안드로이드), WIN (윈도우폰), ETC(기타)
 * MobileApp : 서비스 명
 * startYmd : 시작일
 * endYmd : 종료일
 * </common>
 */

public class DataApiExplorer {

    public static HttpURLConnection getCountData(int numOfRows, int pageNo, int start, int end) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B551011/DataLabService/metcoRegnVisitrDDList"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=qQGmrHZLWFbuo8cT4CGtZpuYOKZDJBHtoMygLD6eC4F8erExrBQEUWda/Z3kNQXSeLNd/Nc1nM6/AYiNTJD47w==");
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(numOfRows), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(pageNo), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("MobileOS", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("ETC", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("MobileApp", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("TravelGo", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("_type", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("startYmd", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(start), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("endYmd", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(end), StandardCharsets.UTF_8));


        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        return conn;
    }


    public static String getInfo(int numOfRows, int pageNo, int start, int end) {
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection conn = DataApiExplorer.getCountData(numOfRows, pageNo, start, end);

            BufferedReader br = DataApiExplorer.getResponseCode(conn); // 상태코드 반환

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

