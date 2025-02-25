package com.travelgo.backend.domain.attraction.service;

import com.travelgo.backend.domain.attraction.dto.AttractionDetailResponse;
import com.travelgo.backend.domain.attraction.dto.CustomAttractionRequest;
import com.travelgo.backend.domain.attraction.dto.CustomAttractionResponse;
import com.travelgo.backend.domain.attraction.entity.Attraction;
import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.attraction.model.BigCategory;
import com.travelgo.backend.domain.attraction.model.MiddleCategory;
import com.travelgo.backend.domain.attraction.model.SmallCategory;
import com.travelgo.backend.domain.attraction.repository.AttractionRepository;
import com.travelgo.backend.domain.attractionImage.entity.AttractionImage;
import com.travelgo.backend.domain.attractionImage.service.AttractionImageService;
import com.travelgo.backend.domain.attractionImage.service.S3UploadService;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.repository.UserRepository;
import com.travelgo.backend.domain.util.entity.geo.service.GeoCodingService;
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
    private final GeoCodingService geoCodingService;
    private final UserRepository userRepository;

    /**
     * 공공데이터 포털 api 연동
     */
    // 세부 관광정보로 명소 저장
    @Transactional
    public List<AttractionDetailResponse> detailInit(String jsonData) {
        Attraction attractionInfo;
        List<Attraction> attractionList = new ArrayList<>();

        try {
            JSONArray array = getJsonArray(jsonData);
            JSONObject getObject;

            for (Object object : array) {
                getObject = (JSONObject) object;

                String[] addr = ((String) getObject.get("addr1")).split(" ");

                if (categoryFiltering(getObject)) continue;

                attractionInfo = Attraction.builder()
                        .attractionName((String) getObject.get("title"))
                        .address((String) getObject.get("addr1"))
                        .city(addr[1])
                        .publicAttractionId(Long.parseLong((String) getObject.get("contentid")))
                        .longitude(Double.parseDouble((String) getObject.get("mapx")))
                        .latitude(Double.parseDouble((String) getObject.get("mapy")))
                        .attractionImageUrl((String) getObject.get("firstimage"))
                        .description((String) getObject.get("overview"))
                        .area(AreaCode.getAreaCode((String) getObject.get("areacode")))
                        .bigCategory(BigCategory.getCategory((String) getObject.get("cat1")))
                        .middleCategory(MiddleCategory.getCategory((String) getObject.get("cat2")))
                        .smallCategory(SmallCategory.getCategory((String) getObject.get("cat3")))
                        .poster("한국관광공사")
                        .likes(0)
                        .customFlag(false)
                        .build();

                if (!isAttractionDuplicated(attractionInfo.getAttractionName())) { // 명소 이름으로 중복 체크
                    attractionList.add(attractionInfo);
                    save(attractionInfo);
                } else {
                    log.info("{}" + "관광지가 이미 존재합니다.", getObject.get("title"));
                }
            }
            checkEmpty(attractionList);

        } catch (CustomException e) {
            throw new CustomException(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attractionList.stream()
                .map(AttractionDetailResponse::new)
                .toList();
    }


    @Transactional
    public List<Long> areaInit(String jsonData) {
        List<Long> contentIdList = new ArrayList<>();
        try {
            JSONArray array = getJsonArray(jsonData);
            JSONObject getObject;

            for (Object object : array) {
                getObject = (JSONObject) object;

                if (categoryFiltering(getObject)) continue;

                if (!isAttractionDuplicated((String) getObject.get("title"))) { // 명소 이름으로 중복 체크
                    contentIdList.add(Long.parseLong((String) getObject.get("contentid")));
                } else {
                    log.info("{}" + "관광지가 이미 존재합니다.", getObject.get("title"));
                }
            }
            checkEmpty(contentIdList);

        } catch (CustomException e) {
            throw new CustomException(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentIdList;
    }

    // 사용자 위치 기반 명소 저장
    @Transactional
    public List<Long> rangeInit(String jsonData) {
        List<Long> contentIdList = new ArrayList<>();

        try {
            JSONArray array = getJsonArray(jsonData);
            JSONObject getObject;

            for (Object object : array) {
                getObject = (JSONObject) object;

                if (categoryFiltering(getObject)) continue;

                if (!isAttractionDuplicated((String) getObject.get("title"))) {// 명소 이름으로 중복 체크
                    contentIdList.add(Long.parseLong((String) getObject.get("contentid")));
                } else {
                    log.info("{}" + "관광지가 이미 존재합니다.", getObject.get("title"));
                }
            }
            checkEmpty(contentIdList);

        } catch (CustomException e) {
            throw new CustomException(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentIdList;
    }
    // 키워드로 명소 저장

    @Transactional
    public List<Long> keywordInit(String jsonData) {
        List<Long> contentIdList = new ArrayList<>();

        try {
            JSONArray array = getJsonArray(jsonData);
            JSONObject getObject;

            for (Object object : array) {
                getObject = (JSONObject) object;

                if (categoryFiltering(getObject)) continue;

                if (!isAttractionDuplicated((String) getObject.get("title"))) { // 명소 이름으로 중복 체크
                    contentIdList.add(Long.parseLong((String) getObject.get("contentid")));
                } else {
                    log.info("{}" + "관광지가 이미 존재합니다.", getObject.get("title"));
                }
            }
            checkEmpty(contentIdList);

        } catch (CustomException e) {
            throw new CustomException(e.getErrorCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentIdList;
    }

    /**
     * 수동 명소 저장
     */

    @Transactional
    public CustomAttractionResponse saveAttraction(CustomAttractionRequest attractionRequest, String email, MultipartFile image) throws IOException {
        if (isAttractionDuplicated(attractionRequest.getAttractionName()))
            throw new CustomException(ErrorCode.DUPLICATED_ATTRACTION);

        String poster = getUser(email).getNickname();

        Attraction attraction = createAttraction(attractionRequest, poster);
        Attraction savedAttraction = attractionRepository.save(attraction);

//        if (!image.isEmpty()) { // 다중 이미지 업로드
//            for (MultipartFile multipartFile : image)
//                attractionImageService.save(multipartFile, attraction);
//        }
        String savedImageURL = attractionImageService.save(image, attraction);
        savedAttraction.initialAttractionImageUrl(savedImageURL);

        return CustomAttractionResponse.of(savedAttraction);
    }

    @Transactional
    public void save(Attraction attraction) {
        attractionRepository.save(attraction);
    }

    @Transactional
    public void delete(Long attractionId) {
        //단일 이미지
        AttractionImage image = attractionImageService.getImage(attractionId);
        s3UploadService.fileDelete(image.getAttractionImageUrl());

//        //다중 이미지
//        List<AttractionImage> images = attractionImageService.getImages(attractionId);
//
//        if (!images.isEmpty()) {
//            for (AttractionImage image : images)
//                s3UploadService.fileDelete(image.getAttractionImageUrl());
//        }
//        attractionImageService.deleteAllById(attractionId); // s3 다중 이미지
        attractionImageService.deleteByAttractionId(attractionId); // s3 단일 이미지
        Attraction attraction = getAttraction(attractionId);
        attractionRepository.delete(attraction);
    }

    //
//    @Transactional
//    public void deleteAll() {
//        attractionImageService.deleteAll(); // s3 다중 이미지
//        attractionRepository.deleteAll();
//    }
    @Transactional
    public void pressLikes(Long attractionId) {
        Attraction attraction = getAttraction(attractionId);
        attraction.plusLikes();
    }

    @Transactional
    public void cancelLikes(Long attractionId) {
        Attraction attraction = getAttraction(attractionId);
        if (attraction.getLikes() > 0)
            attraction.minusLikes();
    }

    /**
     * DTO 변환, 검색 메서드
     */

    @Transactional
    public Attraction createAttraction(CustomAttractionRequest attractionRequest, String poster) {
        Double[] geocode = geoCodingService.geocode(attractionRequest.getAddress());

        return Attraction.builder()
                .attractionName(attractionRequest.getAttractionName())
                .poster(poster)
                .address(attractionRequest.getAddress())
                .latitude(geocode[0])
                .longitude(geocode[1])
                .description(attractionRequest.getDescription())
                .city(attractionRequest.getAddress().split(" ")[1])
                .area(attractionRequest.getArea())
                .bigCategory(attractionRequest.getBigCategory())
                .middleCategory(attractionRequest.getMiddleCategory())
                .smallCategory(attractionRequest.getSmallCategory())
                .likes(0)
                .customFlag(true)
                .build();
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
    }

    public Attraction getAttraction(Long attractionId) {
        return attractionRepository.findById(attractionId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ATTRACTION));
    }


    public AttractionDetailResponse getDetail(Long attractionId) {
        Attraction attraction = getAttraction(attractionId);
        if (attraction.getCustomFlag())
            throw new CustomException(ErrorCode.NOT_CUSTOM_ATTRACTION);

        return createAttractionResponse(attraction);
    }

    public CustomAttractionResponse getCustomDetail(Long attractionId) {
        Attraction attraction = getAttraction(attractionId);
        if (!attraction.getCustomFlag())
            throw new CustomException(ErrorCode.NOT_CUSTOM_ATTRACTION);
        return createCustomAttractionResponse(attraction);
    }


    private static AttractionDetailResponse createAttractionResponse(Attraction attraction) {
        return AttractionDetailResponse.of(attraction);
    }

    private static CustomAttractionResponse createCustomAttractionResponse(Attraction attraction) {
        return CustomAttractionResponse.of(attraction);
    }


    public List<Attraction> getAttractionsWithInDistance(Double latitude, Double longitude, Double distance) {
        return attractionRepository.findAttractionsWithInDistance(latitude, longitude, distance);
    }

    public List<AttractionDetailResponse> getList() {
        return attractionRepository.findAll().stream()
                .map(Attraction::getAttractionId)
                .map(this::getDetail)
                .toList();
    }

    public List<AttractionDetailResponse> getListByArea(AreaCode areaCode) {
        return attractionRepository.findAllByAreaAndCustomFlagFalse(areaCode).stream()
                .map(Attraction::getAttractionId)
                .map(this::getDetail)
                .toList();
    }

    public List<CustomAttractionResponse> getCustomList() {
        return attractionRepository.findAllByCustomFlagTrueOrderByLikesDesc()
                .stream()
                .map(Attraction::getAttractionId)
                .map(this::getCustomDetail)
                .toList();
    }

    /**
     * 검증 메서드
     */

    private boolean isAttractionDuplicated(String name) {
        return attractionRepository.findByAttractionNameAndCustomFlagFalse(name) != null;
    }

    private static void checkEmpty(List<?> list) {
        if (list.isEmpty())
            throw new CustomException(ErrorCode.EMPTY_VALUE);
    }

    private boolean categoryFiltering(JSONObject getObject) {
        return (BigCategory.getCategory((String) getObject.get("cat1")) == null ||
                MiddleCategory.getCategory((String) getObject.get("cat2")) == null ||
                SmallCategory.getCategory((String) getObject.get("cat3")) == null);
    }

    /**
     * api 데이터 파싱 메서드
     */

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
