package com.travelgo.backend.domain.attractionachievement.controller;

import com.travelgo.backend.domain.attractionachievement.dto.AttractionAllInfo;
import com.travelgo.backend.domain.attractionachievement.dto.AttractionAreaInfo;
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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "방문 기록(명소)", description = "방문 기록 API(#20)")
@RequestMapping("/api/attraction-achievement")
public class AttractionAchievementController {
    private final AttractionAchievementService attractionAchievementService;

    /**
     * 조회 메서드
     */

    @Operation(summary = "방문 명소 기록", description = "유저의 방문한 명소의 기록을 가져온다.")
    @GetMapping()
    public ResponseEntity<Map<String, Object>> getAllInfo(@RequestParam(name = "email") String email) {
        return new ResponseEntity<>(attractionAchievementService.getAttractionAchievement(email), HttpStatusCode.valueOf(200));
    }

}
