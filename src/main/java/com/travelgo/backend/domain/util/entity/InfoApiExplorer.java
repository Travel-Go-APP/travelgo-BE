package com.travelgo.backend.domain.util.entity;

import com.travelgo.backend.domain.attraction.model.AreaCode;

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
 * contentTypeId : 관광타입(12:관광지, 14:문화시설, 15:축제공연행사, 25:여행코스, 28:레포츠, 32:숙박, 38:쇼핑, 39:음식점) ID
 * </common>
 *
 * <locationDetail>
 * contentId : 콘텐츠ID
 * defaultYN : 기본정보조회여부( Y,N )
 * firstImageYN : 원본, 썸네일대표 이미지, 이미지 공공누리유형정보 조회여부( Y,N )
 * areacodeYN : 지역코드, 시군구코드조회여부( Y,N )
 * catcodeYN : 대,중,소분류코드조회여부( Y,N )
 * addrinfoYN : 주소, 상세주소조회여부( Y,N )
 * mapinfoYN : 좌표X, Y 조회여부( Y,N )
 * overviewYN : 콘텐츠개요조회여부( Y,N )
 * </locationDetail>
 *
 * <locationBase>
 * mapX : 경도 좌표
 * mapY : 위도 좌표
 * radius : 거리반경(단위:m) , Max값 20000m=20Km
 * </locationBase>
 */

public class InfoApiExplorer {

    public static final String SERVICE_KEY = "=qQGmrHZLWFbuo8cT4CGtZpuYOKZDJBHtoMygLD6eC4F8erExrBQEUWda/Z3kNQXSeLNd/Nc1nM6/AYiNTJD47w==";

    public static HttpURLConnection getArea(int numOfRows, int pageNo, AreaCode areaCode) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B551011/KorService1/areaBasedList1"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + SERVICE_KEY);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(numOfRows), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(pageNo), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("MobileOS", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("ETC", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("MobileApp", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("TravelGo", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("_type", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("listYN", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("Y", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("arrange", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("A", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("contentTypeId", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(12), StandardCharsets.UTF_8));

        urlBuilder.append("&" + URLEncoder.encode("areaCode", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(areaCode.getCode()), StandardCharsets.UTF_8));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        return conn;
    }

    public static HttpURLConnection getDetail(int numOfRows, int pageNo, Long contentId) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B551011/KorService1/detailCommon1"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + SERVICE_KEY);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(numOfRows), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(pageNo), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("MobileOS", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("ETC", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("MobileApp", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("TravelGo", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("_type", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("defaultYN", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("Y", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("firstImageYN", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("Y", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("areacodeYN", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("Y", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("catcodeYN", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("Y", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("addrinfoYN", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("Y", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("mapinfoYN", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("Y", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("overviewYN", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("Y", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("contentTypeId", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(12), StandardCharsets.UTF_8));

        urlBuilder.append("&" + URLEncoder.encode("contentId", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(contentId), StandardCharsets.UTF_8));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        return conn;
    }

    public static HttpURLConnection getRange(int numOfRows, int pageNo, double longitude, double latitude, int radius) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B551011/KorService1/locationBasedList1"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + SERVICE_KEY);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(numOfRows), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(pageNo), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("MobileOS", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("ETC", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("MobileApp", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("TravelGo", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("_type", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("listYN", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("Y", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("arrange", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("A", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("contentTypeId", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(12), StandardCharsets.UTF_8));

        urlBuilder.append("&" + URLEncoder.encode("mapX", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(longitude), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("mapY", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(latitude), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("radius", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(radius), StandardCharsets.UTF_8));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        return conn;
    }

    public static HttpURLConnection getKeyword(int numOfRows, int pageNo, String keyword) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/B551011/KorService1/searchKeyword1"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + SERVICE_KEY);
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(numOfRows), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("pageNo", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(pageNo), StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("MobileOS", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("ETC", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("MobileApp", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("TravelGo", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("_type", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("json", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("listYN", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("Y", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("arrange", StandardCharsets.UTF_8) + "=" + URLEncoder.encode("A", StandardCharsets.UTF_8));
        urlBuilder.append("&" + URLEncoder.encode("contentTypeId", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(String.valueOf(12), StandardCharsets.UTF_8));

        urlBuilder.append("&" + URLEncoder.encode("keyword", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(keyword, StandardCharsets.UTF_8));

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        return conn;
    }


    // 지역 기반
    public static String getAreaInfo(int numOfRows, int pageNo, AreaCode areaCode) {
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection conn = InfoApiExplorer.getArea(numOfRows, pageNo, areaCode);

            BufferedReader br = InfoApiExplorer.getResponseCode(conn); // 상태코드 반환

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

    // 세부 정보
    public static String getDetailInfo(int numOfRows, int pageNo, Long contentId) {
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection conn = InfoApiExplorer.getDetail(numOfRows, pageNo, contentId);

            BufferedReader br = InfoApiExplorer.getResponseCode(conn); // 상태코드 반환

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


    // 현재 위치 기반
    public static String getRangeInfo(int numOfRows, int pageNo, double longitude, double latitude, int radius) {
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection conn = InfoApiExplorer.getRange(numOfRows, pageNo, longitude, latitude, radius);

            BufferedReader br = InfoApiExplorer.getResponseCode(conn); // 상태코드 반환

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


    // 키워드 기반
    public static String getKeywordInfo(int numOfRows, int pageNo, String keyword) {
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection conn = InfoApiExplorer.getKeyword(numOfRows, pageNo, keyword);

            BufferedReader br = InfoApiExplorer.getResponseCode(conn); // 상태코드 반환

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
