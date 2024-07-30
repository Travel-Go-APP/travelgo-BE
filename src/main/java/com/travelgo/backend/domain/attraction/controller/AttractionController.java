package com.travelgo.backend.domain.attraction.controller;

import com.travelgo.backend.domain.attraction.dto.AttractionRequest;
import com.travelgo.backend.domain.attraction.dto.AttractionDetailResponse;
import com.travelgo.backend.domain.attraction.entity.DataApiExplorer;
import com.travelgo.backend.domain.attraction.entity.InfoApiExplorer;
import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.attraction.service.AttractionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Data;
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

    /**
     * 삭제 메서드
     */

    @Operation(summary = "Id로 명소 삭제", description = "단일 명소 삭제")
    @DeleteMapping("/{attractionId}")
    public ResponseEntity<?> deleteAttraction(@PathVariable("attractionId") Long attractionId) {

        attractionService.delete(attractionId);
        return ResponseEntity.ok(HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "전체 명소 삭제", description = "전체 명소를 삭제합니다.")
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAll() {
        attractionService.deleteAll();
        return ResponseEntity.ok(HttpStatusCode.valueOf(200));
    }

    /**
     * 생성 메서드
     */

    @Operation(summary = "수동 명소 저장", description = "수동으로 정보를 작성해 명소를 저장한다.")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveAttraction(@Valid @RequestPart(value = "attractionRequest") AttractionRequest attractionRequest,
                                            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {

        return new ResponseEntity<>(attractionService.saveAttraction(attractionRequest, image), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "공공 데이터 포털 세부 정보 api 정보 저장", description = "세부 관광지 정보를 db에 저장한다.")
    @PostMapping("/save/detail")
    public ResponseEntity<?> saveDetailApi(@RequestParam(name = "numOfRows") int numOfRows,
                                        @RequestParam(name = "pageNo") int pageNo,
                                        @RequestParam(name = "attractionId") Long attractionId) {
        String result = InfoApiExplorer.getDetailInfo(numOfRows, pageNo, attractionId);
        attractionService.detailInit(result);

        return ResponseEntity.ok(HttpStatusCode.valueOf(200));

    }

    @Operation(summary = "공공 데이터 포털 지역별 api 정보 저장", description = "지역별 관광지 정보를 db에 저장한다.")
    @PostMapping("/save/area")
    public ResponseEntity<?> saveAreaApi(@RequestParam(name = "numOfRows") int numOfRows,
                                      @RequestParam(name = "pageNo") int pageNo,
                                      @RequestParam(name = "areaCode") AreaCode areaCode) {
        String result = InfoApiExplorer.getAreaInfo(numOfRows, pageNo, areaCode);
        List<Long> contentIdList = attractionService.areaInit(result);

        for (Long id : contentIdList)
            saveDetailApi(numOfRows, pageNo, id);

        return ResponseEntity.ok(HttpStatusCode.valueOf(200));

    }

    @Operation(summary = "공공 데이터 포털 위치기반 api 정보 저장", description = "현재 위치에서 반경 radius 안에 있는 관광지를 db에 저장한다.")
    @PostMapping("/save/range")
    public ResponseEntity<?> saveRangeApi(@RequestParam(name = "numOfRows") int numOfRows,
                                       @RequestParam(name = "pageNo") int pageNo,
                                       @RequestParam(name = "longitude") double longitude,
                                       @RequestParam(name = "latitude") double latitude,
                                       @RequestParam(name = "radius") int radius) {
        String result = InfoApiExplorer.getRangeInfo(numOfRows, pageNo, longitude, latitude, radius);
        List<Long> contentIdList = attractionService.rangeInit(result);

        for (Long id : contentIdList)
            saveDetailApi(numOfRows, pageNo, id);

        return ResponseEntity.ok(HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "공공 데이터 포털 키워드 api 정보 저장", description = "키워드로 검색된 관광지를 db에 저장한다.")
    @PostMapping("/save/keyword")
    public ResponseEntity<?> saveKeywordApi(@RequestParam(name = "numOfRows") int numOfRows,
                                         @RequestParam(name = "pageNo") int pageNo,
                                         @RequestParam(name = "keyword") String keyword) {
        String result = InfoApiExplorer.getKeywordInfo(numOfRows, pageNo, keyword);
        List<Long> contentIdList = attractionService.keywordInit(result);

        for (Long id : contentIdList)
            saveDetailApi(numOfRows, pageNo, id);

        return ResponseEntity.ok(HttpStatusCode.valueOf(200));
    }

    /**
     * 조회 메서드
     */

    @Operation(summary = "DB에 저장된 단일 명소 검색", description = "DB에 저장된 단일 명소를 가져옵니다.")
    @GetMapping("/{attractionId}")
    public ResponseEntity<AttractionDetailResponse> findAttractionById(@PathVariable(name = "attractionId") Long attractionId) {
        return new ResponseEntity<>(attractionService.getDetail(attractionId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "DB에 저장된 전체 명소 검색", description = "DB에 저장된 전체 명소를 가져옵니다.")
    @GetMapping("/all")
    public ResponseEntity<Result<List<AttractionDetailResponse>>> findAll() {
        List<AttractionDetailResponse> responseList = attractionService.getList();

        return new ResponseEntity<>(new Result<>(responseList.size(), responseList), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "지역별 DB에 저장된 전체 명소 검색", description = "지역별 DB에 저장된 전체 명소를 가져옵니다.")
    @GetMapping("/area")
    public ResponseEntity<Result<List<AttractionDetailResponse>>> findAllByArea(@RequestParam(name = "AreaCode") AreaCode areaCode) {
        List<AttractionDetailResponse> responseList = attractionService.getListByArea(areaCode);

        return new ResponseEntity<>(new Result<>(responseList.size(), responseList), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "공공 데이터 포털 방문자 수 api 실행", description = "지자체별 방문자 수를 받는 api를 실행한다.")
    @GetMapping("/load/location-data")
    public ResponseEntity<String> loadCountApi(@RequestParam(name = "numOfRows") int numOfRows,
                                               @RequestParam(name = "pageNo") int pageNo,
                                               @RequestParam(name = "startDate") int startDate,
                                               @RequestParam(name = "endDate") int endDate) {
        String result = DataApiExplorer.getCountInfo(numOfRows, pageNo, startDate, endDate);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }

    @Data
    static class Result<T> {

        private int count; //검색 개수
        private T data; //검색 데이터

        public Result(T data) {
            this.data = data;
        }

        public Result(int count, T data) {
            this.count = count;
            this.data = data;
        }
    }

    //    @Operation(summary = "공공 데이터 포털 지역별 정보 api 실행", description = "지역별 관광 정보를 불러온다.")
//    @GetMapping("/load/area")
//    public ResponseEntity<String> loadAreaApi(@RequestParam(name = "numOfRows", defaultValue = "10") int numOfRows,
//                                              @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
//                                              @RequestParam(name = "areaCode") AreaCode areaCode) {
//        String result = InfoApiExplorer.getAreaInfo(numOfRows, pageNo, areaCode);
//        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
//    }
//
//    @Operation(summary = "공공 데이터 포털 상세 정보 api 실행", description = "타입별 상세 관광 정보를 불러온다.")
//    @GetMapping("/load/detail")
//    public ResponseEntity<String> loadDetailApi(@RequestParam(name = "numOfRows", defaultValue = "10") int numOfRows,
//                                                @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
//                                                @RequestParam(name = "contentId") Long contentId) {
//        String result = InfoApiExplorer.getDetailInfo(numOfRows, pageNo, contentId);
//        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
//    }
//
//    @Operation(summary = "공공 데이터 포털 위치 기반 api 실행", description = "현재 위치에서 반경 radius 안에 있는 관광 정보를 불러온다.")
//    @GetMapping("/load/range")
//    public ResponseEntity<String> loadRangeApi(@RequestParam(name = "numOfRows", defaultValue = "10") int numOfRows,
//                                               @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
//                                               @RequestParam(name = "longitude") double longitude,
//                                               @RequestParam(name = "latitude") double latitude,
//                                               @RequestParam(name = "radius") int radius) {
//        String result = InfoApiExplorer.getRangeInfo(numOfRows, pageNo, longitude, latitude, radius);
//        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
//    }
//
//    @Operation(summary = "공공 데이터 포털 키워드 api 실행", description = "키워드로 관광 정보를 불러온다.")
//    @GetMapping("/load/keyword")
//    public ResponseEntity<String> loadKeywordApi(@RequestParam(name = "numOfRows", defaultValue = "10") int numOfRows,
//                                                 @RequestParam(name = "pageNo", defaultValue = "1") int pageNo,
//                                                 @RequestParam(name = "keyword") String keyword) {
//        String result = InfoApiExplorer.getKeywordInfo(numOfRows, pageNo, keyword);
//        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
//    }
}
