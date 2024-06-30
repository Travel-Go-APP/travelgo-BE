package com.travelgo.backend.domain.attraction.controller;

import com.travelgo.backend.domain.attraction.dto.AttractionRequest;
import com.travelgo.backend.domain.attraction.dto.AttractionResponse;
import com.travelgo.backend.domain.attraction.entity.DataApiExplorer;
import com.travelgo.backend.domain.attraction.entity.InfoApiExplorer;
import com.travelgo.backend.domain.attraction.service.AttractionService;
import com.travelgo.backend.global.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "명소", description = "명소 API(#16)")
@RequestMapping("/api/attraction")
public class AttractionController {
    private final AttractionService attractionService;

    @Operation(summary = "명소 저장", description = "정보를 받아 명소를 저장한다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveAttraction(@Valid @RequestPart(value = "attractionRequest") AttractionRequest attractionRequest,
                                            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        return new ResponseEntity<>(attractionService.saveAttraction(attractionRequest, image), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "명소 삭제", description = "단일 명소 삭제")
    @DeleteMapping("/{attractionId}")
    public ResponseEntity<?> deleteAttraction(@PathVariable("attractionId") Long attractionId) {

        try {
            attractionService.delete(attractionId);
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(200));
        } catch (CustomException e) {
            return new ResponseEntity(e.getMessage(), e.getErrorCode().getCode());
        }
    }

    @Operation(summary = "전체 명소 삭제", description = "전체 명소를 삭제합니다.")
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll() {
        attractionService.deleteAll();
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "DB에 저장된 단일 명소 검색", description = "DB에 저장된 단일 명소를 가져옵니다.")
    @GetMapping("/{attractionId}")
    public ResponseEntity<AttractionResponse> findAttraction(@PathVariable(name = "attractionId") Long attractionId) {
        return new ResponseEntity<>(attractionService.getDetail(attractionId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "DB에 저장된 전체 명소 검색", description = "DB에 저장된 전체 명소를 가져옵니다.")
    @GetMapping("/find-all")
    public ResponseEntity<List<AttractionResponse>> findAll() {
        return new ResponseEntity<>(attractionService.getList(), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "공공 데이터 포털 위치기반 api 실행", description = "현재 위치에서 반경 radius 안에 있는 관광지의 정보를 불러온다.")
    @GetMapping("/load/location-base")
    public ResponseEntity<String> loadApi(@RequestParam(name = "numOfRows") int numOfRows,
                                          @RequestParam(name = "pageNo") int pageNo,
                                          @RequestParam(name = "longitude") double longitude,
                                          @RequestParam(name = "latitude") double latitude,
                                          @RequestParam(name = "radius") int radius) {
        String result = InfoApiExplorer.getInfo(numOfRows, pageNo, longitude, latitude, radius);
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "공공 데이터 포털 상세 정보 api 실행", description = "타입별 공통 관광 정보를 불러온다.")
    @GetMapping("/load/location-detail")
    public ResponseEntity<String> loadApi(@RequestParam(name = "numOfRows") int numOfRows,
                                          @RequestParam(name = "pageNo") int pageNo,
                                          @RequestParam(name = "contentId") int contentId) {
        String result = InfoApiExplorer.getInfo(numOfRows, pageNo, contentId);
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "공공 데이터 포털 키워드 api 실행", description = "지역 키워드로 공통 관광 정보를 불러온다.")
    @GetMapping("/load/location-keyword")
    public ResponseEntity<String> loadApi(@RequestParam(name = "numOfRows") int numOfRows,
                                          @RequestParam(name = "pageNo") int pageNo,
                                          @RequestParam(name = "keyword") String keyword) {
        String result = InfoApiExplorer.getInfo(numOfRows, pageNo, keyword);
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "공공 데이터 포털 위치기반 api 정보 저장", description = "현재 위치에서 반경 radius 안에 있는 관광지를 db에 저장한다.")
    @PostMapping("/save/location-base")
    public ResponseEntity<List<AttractionResponse>> saveApi(@RequestParam(name = "numOfRows") int numOfRows,
                                                            @RequestParam(name = "pageNo") int pageNo,
                                                            @RequestParam(name = "longitude") double longitude,
                                                            @RequestParam(name = "latitude") double latitude,
                                                            @RequestParam(name = "radius") int radius) {
        try {
            String result = InfoApiExplorer.getInfo(numOfRows, pageNo, longitude, latitude, radius);
            List<AttractionResponse> responseList = attractionService.locationBaseInit(result);
            return new ResponseEntity<>(responseList, HttpStatusCode.valueOf(200));
        } catch (CustomException e) {
            return new ResponseEntity(e.getMessage(), e.getErrorCode().getCode());
        }

    }

    @Operation(summary = "공공 데이터 포털 공통 정보 api 정보 저장", description = "공통 정보에 있는 관광지를 db에 저장한다.")
    @PostMapping("/save/location-detail")
    public ResponseEntity<List<AttractionResponse>> saveApi(@RequestParam(name = "numOfRows") int numOfRows,
                                                            @RequestParam(name = "pageNo") int pageNo,
                                                            @RequestParam(name = "contentId") int contentId) {
        try {
            String result = InfoApiExplorer.getInfo(numOfRows, pageNo, contentId);
            List<AttractionResponse> responseList = attractionService.locationDetailInit(result);
            return new ResponseEntity<>(responseList, HttpStatusCode.valueOf(200));
        } catch (CustomException e) {
            return new ResponseEntity(e.getMessage(), e.getErrorCode().getCode());
        }
    }

    @Operation(summary = "공공 데이터 포털 키워드 api 정보 저장", description = "키워드로 검색된 관광지를 db에 저장한다.")
    @PostMapping("/save/location-keyword")
    public ResponseEntity<List<AttractionResponse>> saveLocationDetail(@RequestParam(name = "numOfRows") int numOfRows,
                                                                       @RequestParam(name = "pageNo") int pageNo,
                                                                       @RequestParam(name = "keyword") String keyword) {
        try {
            String result = InfoApiExplorer.getInfo(numOfRows, pageNo, keyword);
            List<AttractionResponse> list = attractionService.locationKeywordInit(result);
            return new ResponseEntity<>(list, HttpStatusCode.valueOf(200));
        } catch (CustomException e) {
            return new ResponseEntity(e.getMessage(), e.getErrorCode().getCode());
        }
    }

    @Operation(summary = "공공 데이터 포털 방문자 수 api 실행", description = "지자체별 방문자 수를 받는 api를 실행한다.")
    @GetMapping("/load/location-data")
    public ResponseEntity<String> loadApi(@RequestParam(name = "numOfRows") int numOfRows,
                                          @RequestParam(name = "pageNo") int pageNo,
                                          @RequestParam(name = "startDate") int startDate,
                                          @RequestParam(name = "endDate") int endDate) {
        String result = DataApiExplorer.getInfo(numOfRows, pageNo, startDate, endDate);
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }
}
