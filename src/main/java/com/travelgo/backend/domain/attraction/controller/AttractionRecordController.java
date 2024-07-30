package com.travelgo.backend.domain.attraction.controller;

import com.travelgo.backend.domain.attraction.dto.AttractionResponse;
import com.travelgo.backend.domain.attraction.service.AttractionRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "조사하기(명소)", description = "명소 조사하기 API(#24)")
@RequestMapping("/api/attraction-record")
public class AttractionRecordController {
    private final AttractionRecordService attractionRecordService;

    /**
     * 조회 메서드
     */

    @Operation(summary = "위치기반 지역별 미방문 명소 리스트", description = "조사하기를 눌렀을때 현재 유저가 방문하지 않은 기록을 가져온다.")
    @GetMapping
    public ResponseEntity<List<AttractionResponse>> getUnvisitListByRadius(@RequestParam(name = "email") String email,
                                                                           @RequestParam(name = "latitude") Double latitude,
                                                                           @RequestParam(name = "longitude") Double longitude,
                                                                           @RequestParam(name = "distance", defaultValue = "1") Double distance) {
        return new ResponseEntity<>(attractionRecordService.getunVisitAttractionWithInDistance(email, latitude, longitude, distance), HttpStatusCode.valueOf(200));
    }
}
