package com.travelgo.backend.domain.attractionachievement.controller;

import com.travelgo.backend.domain.attractionachievement.dto.AllInfo;
import com.travelgo.backend.domain.attractionachievement.dto.AttractionInfo;
import com.travelgo.backend.domain.attractionachievement.service.AttractionAchievementService;
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
@Tag(name = "명소 방문 정보", description = "명소 방문 정보 API(#20)")
@RequestMapping("/api/attraction-achievement")
public class AttractionInfoController {
    private final AttractionAchievementService attractionAchievementService;

    /**
     * 조회 메서드
     */

    @Operation(summary = "전체 방문 명소 개수", description = "전체 명소중 유저가 방문한 명소 개수를 가져온다.")
    @GetMapping("/all")
    public ResponseEntity<AllInfo> getAllInfo(@RequestParam(name = "email") String email) {
        return new ResponseEntity<>(attractionAchievementService.getAllInfo(email), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "지자체별 방문한 명소 개수", description = "지자체별로 유저가 방문한 명소 개수를 가져온다.")
    @GetMapping("/city")
    public ResponseEntity<List<AttractionInfo>> getAttrationInfoList(@RequestParam(name = "email") String email) {
        return new ResponseEntity<>(attractionAchievementService.getAttractionInfo(email), HttpStatusCode.valueOf(200));
    }

}
