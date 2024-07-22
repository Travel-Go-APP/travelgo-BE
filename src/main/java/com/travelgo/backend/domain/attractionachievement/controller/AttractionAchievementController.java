package com.travelgo.backend.domain.attractionachievement.controller;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.attractionachievement.dto.AttractionAchievementRequest;
import com.travelgo.backend.domain.attractionachievement.dto.AttractionAchievementResponse;
import com.travelgo.backend.domain.attractionachievement.service.AttractionAchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "명소 방문", description = "명소 방문 API(#20)")
@RequestMapping("/api/attraction-achievement")
public class AttractionAchievementController {
    private final AttractionAchievementService attractionAchievementService;

    /**
     * 생성 메서드
     */
    @Operation(summary = "명송 방문 기록 저장", description = "유저가 명소에 방문 기록을 저장한다.")
    @PostMapping
    public ResponseEntity<AttractionAchievementResponse> saveAchievement(@Valid @RequestBody AttractionAchievementRequest attractionAchievementRequest) {
        return new ResponseEntity<>(attractionAchievementService.saveAttractionAchievement(attractionAchievementRequest),
                HttpStatusCode.valueOf(200));
    }

    /**
     * 삭제 메서드
     */
    @Operation(summary = "명송 방문 기록 삭제", description = "유저가 명소에 방문한 기록을 삭제한다.")
    @DeleteMapping
    public ResponseEntity<?> deleteAchievement(@Valid @RequestBody AttractionAchievementRequest attractionAchievementRequest) {
        attractionAchievementService.deleteAttractionAchievement(attractionAchievementRequest);

        return ResponseEntity.ok(HttpStatusCode.valueOf(200));
    }


    /**
     * 조회 메서드
     */
    @Operation(summary = "지역별 명송 방문 기록 리스트", description = "지역별 유저가 명소에 방문한 기록을 가져온다.")
    @GetMapping("/list")
    public ResponseEntity<List<AttractionAchievementResponse>> getList(@RequestParam(name = "email") String email,
                                                                       @RequestParam(name = "area") AreaCode area) {
        return new ResponseEntity<>(attractionAchievementService.getList(email, area), HttpStatusCode.valueOf(200));
    }

}
