package com.travelgo.backend.domain.user.service;

import com.travelgo.backend.domain.user.dto.Response.KakaoGeoResponse;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(GeoCodingService.class);

    private final String KAKAO_REST_API_KEY = "4985bb6e1259e1eea63d88a7decc596b";
    private final String KAKAO_GEOCODE_URL = "https://dapi.kakao.com/v2/local/geo/coord2address.json";

    public String reverseGeocode(Double latitude, Double longitude) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = UriComponentsBuilder.fromHttpUrl(KAKAO_GEOCODE_URL)
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
                logger.info("Kakao API response: {}", response.getBody());
                List<KakaoGeoResponse.Document> documents = response.getBody().getDocuments();
                if (documents != null && !documents.isEmpty()) {
                    KakaoGeoResponse.Document document = documents.get(0);
                    if (document.getAddress() != null) {
                        String fullAddress = document.getAddress().getAddressName();
                        String area = extractRegion(fullAddress);
                        return area;
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
    private String extractRegion(String fullAddress) {
        // 주소 문자열에서 첫 번째 단어를 추출
        if (fullAddress != null && !fullAddress.isEmpty()) {
            String[] parts = fullAddress.split(" ");
            if (parts.length > 0) {
                return parts[0];
            }
        }
        return null;
    }
}