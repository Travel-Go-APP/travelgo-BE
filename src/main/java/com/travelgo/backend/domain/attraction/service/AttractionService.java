package com.travelgo.backend.domain.attraction.service;

import com.travelgo.backend.domain.area.entity.AreaCode;
import com.travelgo.backend.domain.attraction.dto.AttractionRequest;
import com.travelgo.backend.domain.attraction.dto.AttractionResponse;
import com.travelgo.backend.domain.attraction.entity.Attraction;
import com.travelgo.backend.domain.attraction.repository.AttractionRepository;
import com.travelgo.backend.domain.attractionImage.entity.AttractionImage;
import com.travelgo.backend.domain.attractionImage.service.AttractionImageService;
import com.travelgo.backend.domain.attractionImage.service.S3UploadService;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttractionService {
    private final AttractionRepository attractionRepository;
    private final S3UploadService s3UploadService;
    private final AttractionImageService attractionImageService;

    /**
     * 공공데이터 포털 api 연동
     */

    // 사용자 위치 기반 명소 저장
    @Transactional
    public List<AttractionResponse> locationBaseInit(String jsonData) {
        Attraction attractionInfo = null;
        List<Attraction> attractionList = new ArrayList<>();

        try {
            JSONObject getObject;
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
            JSONObject parseResponse = (JSONObject) jsonObject.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            JSONObject items = (JSONObject) parseBody.get("items");
            JSONArray array = (JSONArray) items.get("item");

            for (Object object : array) {
                getObject = (JSONObject) object;

                attractionInfo = Attraction.builder()
                        .attractionName((String) getObject.get("title"))
                        .address((String) getObject.get("addr1"))
                        .longitude(Double.parseDouble((String) getObject.get("mapx")))
                        .latitude(Double.parseDouble((String) getObject.get("mapy")))
                        .attractionImageUrl((String) getObject.get("firstimage"))
                        .area(AreaCode.getAreaCode((String) getObject.get("areacode")))
                        .description(null)
                        .homepage(null)
                        .hiddenFlag(false)
                        .build();

                if (!isAttractionDuplicated(attractionInfo)) { // 명소 이름으로 중복 체크
                    attractionList.add(attractionInfo);
                    save(attractionInfo);
//                    attractionImageService.save((String) getObject.get("firstimage"), saveAttraction);
                } else
                    throw new CustomException(ErrorCode.DUPLICATED_ATTRACTION);
            }
            log.info(String.valueOf(attractionInfo));

        } catch (CustomException e) {
            throw new CustomException(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attractionList.stream()
                .map(AttractionResponse::new)
                .toList();
    }

    // 공통 관광정보로 명소 저장
    @Transactional
    public List<AttractionResponse> locationDetailInit(String jsonData) {
        Attraction attractionInfo = null;
        List<Attraction> attractionList = new ArrayList<>();

        try {
            JSONObject getObject;
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
            JSONObject parseResponse = (JSONObject) jsonObject.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            JSONObject items = (JSONObject) parseBody.get("items");
            JSONArray array = (JSONArray) items.get("item");

            for (Object object : array) {
                getObject = (JSONObject) object;

                attractionInfo = Attraction.builder()
                        .attractionName((String) getObject.get("title"))
                        .address((String) getObject.get("addr1"))
                        .longitude(Double.parseDouble((String) getObject.get("mapx")))
                        .latitude(Double.parseDouble((String) getObject.get("mapy")))
                        .attractionImageUrl((String) getObject.get("firstimage"))
                        .description((String) getObject.get("overview"))
                        .homepage((String) getObject.get("homepage"))
                        .area(AreaCode.getAreaCode((String) getObject.get("areacode")))
                        .hiddenFlag(false)
                        .build();


                if (!isAttractionDuplicated(attractionInfo)) { // 명소 이름으로 중복 체크
                    attractionList.add(attractionInfo);
                    save(attractionInfo);
//                    attractionImageService.save((String) getObject.get("firstimage"), saveAttraction); // 다중 이미지 저장
                } else
                    throw new CustomException(ErrorCode.DUPLICATED_ATTRACTION);
            }
            log.info(String.valueOf(attractionInfo));

        } catch (CustomException e) {
            throw new CustomException(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attractionList.stream()
                .map(AttractionResponse::new)
                .toList();
    }

    // 키워드로 명소 저장
    @Transactional
    public List<AttractionResponse> locationKeywordInit(String jsonData) {
        Attraction attractionInfo = null;
        List<Attraction> attractionList = new ArrayList<>();

        try {
            JSONObject getObject;
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonData);
            JSONObject parseResponse = (JSONObject) jsonObject.get("response");
            JSONObject parseBody = (JSONObject) parseResponse.get("body");
            JSONObject items = (JSONObject) parseBody.get("items");
            JSONArray array = (JSONArray) items.get("item");

            for (Object object : array) {
                getObject = (JSONObject) object;

                attractionInfo = Attraction.builder()
                        .attractionName((String) getObject.get("title"))
                        .address((String) getObject.get("addr1"))
                        .longitude(Double.parseDouble((String) getObject.get("mapx")))
                        .latitude(Double.parseDouble((String) getObject.get("mapy")))
                        .attractionImageUrl((String) getObject.get("firstimage"))
                        .area(AreaCode.getAreaCode((String) getObject.get("areacode")))
                        .description(null)
                        .homepage(null)
                        .hiddenFlag(false)
                        .build();

                if (!isAttractionDuplicated(attractionInfo)) { // 명소 이름으로 중복 체크
                    attractionList.add(attractionInfo);
                    save(attractionInfo);
//                    attractionImageService.save((String) getObject.get("firstimage"), saveAttraction);
                } else
                    throw new CustomException(ErrorCode.DUPLICATED_ATTRACTION);
            }
            log.info(String.valueOf(attractionInfo));

        } catch (CustomException e) {
            throw new CustomException(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attractionList.stream()
                .map(AttractionResponse::new)
                .toList();
    }

    @Transactional
    public AttractionResponse saveAttraction(AttractionRequest attractionRequest, MultipartFile image) throws IOException {
        Attraction attraction = createAttraction(attractionRequest);
        Attraction savedAttraction = attractionRepository.save(attraction);

//        if(!image.isEmpty()){ // 다중 이미지 업로드
//            for (MultipartFile multipartFile : image)
//                attractionImageService.save(multipartFile, attraction);
//        }
        attractionImageService.save(image, attraction); //단일 이미지 업로드

        return getDetail(savedAttraction.getAttractionId());
    }

    @Transactional
    public Attraction save(Attraction attraction) {
        return attractionRepository.save(attraction);
    }

    @Transactional
    public void delete(Long attractionId) {
//        List<AttractionImage> images = attractionImageService.getImages(attractionId);
//
//        if (!images.isEmpty()) {
//            for (AttractionImage image : images)
//                s3UploadService.fileDelete(image.getAttractionImageUrl());
//        }

//        attractionImageService.deleteAllById(attractionId); // s3 다중 이미지
        Attraction attraction = getAttraction(attractionId);
        attractionRepository.delete(attraction);
    }

    @Transactional
    public void deleteAll() {
//        attractionImageService.deleteAll(); // s3 다중 이미지
        attractionRepository.deleteAll();
    }

    public List<AttractionResponse> getList() {
        return attractionRepository.findAll().stream()
                .map(Attraction::getAttractionId)
                .map(this::getDetail)
                .toList();
    }

    public AreaCode getArea(String code) {
        AreaCode area = AreaCode.valueOf(code);
        return area;
    }

    public AttractionResponse getDetail(Long attractionId) {
        Attraction attraction = getAttraction(attractionId);
        return createAttractionResponse(attraction);
    }

    @Transactional
    public Attraction createAttraction(AttractionRequest attractionRequest) {
        return Attraction.builder()
                .attractionName(attractionRequest.getAttractionName())
                .homepage(attractionRequest.getHomepage())
                .address(attractionRequest.getAddress())
                .latitude(attractionRequest.getLatitude())
                .longitude(attractionRequest.getLongitude())
                .description(attractionRequest.getDescription())
                .area(attractionRequest.getArea())
                .hiddenFlag(false)
                .build();
    }

    private Attraction getAttraction(Long attractionId) {
        return attractionRepository.findById(attractionId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ATTRACTION));
    }

    private static AttractionResponse createAttractionResponse(Attraction attraction) {
        return new AttractionResponse(attraction);
    }

    private boolean isAttractionDuplicated(Attraction attraction) {
        return attractionRepository.findByAttractionName(attraction.getAttractionName()) != null;
    }

}
