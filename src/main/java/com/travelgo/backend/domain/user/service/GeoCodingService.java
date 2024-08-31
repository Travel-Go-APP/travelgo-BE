package com.travelgo.backend.domain.user.service;

import com.travelgo.backend.domain.user.dto.Response.KakaoGeoResponse;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class GeoCodingService {
    private final String KAKAO_REST_API_KEY = "4985bb6e1259e1eea63d88a7decc596b";
    private final String KAKAO_REVERSE_GEOCODE_URL = "https://dapi.kakao.com/v2/local/geo/coord2address.json";
    private final String KAKAO_GEOCODE_URL = "https://dapi.kakao.com/v2/local/search/address";
    public String[] reverseGeocode(Double latitude, Double longitude) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = UriComponentsBuilder.fromHttpUrl(KAKAO_REVERSE_GEOCODE_URL)
                .queryParam("x", longitude)
                .queryParam("y", latitude)
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KAKAO_REST_API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<KakaoGeoResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, KakaoGeoResponse.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<KakaoGeoResponse.Document> documents = response.getBody().getDocuments();
                if (documents != null && !documents.isEmpty()) {
                    KakaoGeoResponse.Document document = documents.get(0);
                    if (document.getAddress() != null) {
                        String fullAddress = document.getAddress().getAddressName();
                        String[] areaAndVisitArea = extractRegion(fullAddress);
                        return areaAndVisitArea; // 지역명과 방문 지역명을 모두 반환
                    } else {
                        throw new CustomException(ErrorCode.NOT_FOUND_GEO);
                    }
                }
            } else {
                throw new CustomException(ErrorCode.NOT_FOUND_KAKAO);
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_KAKAO);
        }

        return null;
    }

    public Double[] geocode(String address) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = UriComponentsBuilder.fromHttpUrl(KAKAO_GEOCODE_URL)
                .queryParam("query", address)
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KAKAO_REST_API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<KakaoGeoResponse> response = restTemplate.exchange(uri, HttpMethod.GET, entity, KakaoGeoResponse.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List<KakaoGeoResponse.Document> documents = response.getBody().getDocuments();
                if (documents != null && !documents.isEmpty()) {
                    KakaoGeoResponse.Document document = documents.get(0);
                    if (document.getX() != null && document.getY() != null) {
                        double latitude = document.getY();
                        double longitude = document.getX();
                        return new Double[]{latitude, longitude}; // 위도와 경도 반환
                    } else {
                        throw new CustomException(ErrorCode.NOT_FOUND_GEO);
                    }
                }
            } else {
                throw new CustomException(ErrorCode.NOT_FOUND_KAKAO);
            }
        } catch (Exception e) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_KAKAO);
        }

        return null;
    }

    private String[] extractRegion(String fullAddress) {
        if (fullAddress != null && !fullAddress.isEmpty()) {
            String[] parts = fullAddress.split(" ");
            if (parts.length > 1) {
                return new String[]{parts[0], parts[1]};  // "경기"와 "평택시"로 분리
            } else if (parts.length == 1) {
                return new String[]{parts[0], ""};  // 하나의 부분만 있는 경우
            }
        }
        return new String[]{null, null};  // 주소가 없을 때
    }
}