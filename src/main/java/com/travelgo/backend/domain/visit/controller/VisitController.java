package com.travelgo.backend.domain.visit.controller;

import com.travelgo.backend.auth.utils.SecurityUtil;
import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.visit.dto.VisitResponse;
import com.travelgo.backend.domain.visit.service.VisitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "방문 하기(명소)", description = "명소 방문 API(#24)")
@RequestMapping("/api/visit")
public class VisitController {
    private final VisitService visitService;

    /**
     * 생성 메서드
     */
    @Operation(summary = "명송 방문 기록 저장", description = "유저가 명소에 방문 기록을 저장한다.")
    @PostMapping
    public ResponseEntity<VisitResponse> saveAchievement(Authentication authentication,
                                                         @RequestParam(name = "attractionId") Long attractionId) {
        return new ResponseEntity<>(visitService.saveAttractionAchievement(SecurityUtil.getCurrentName(authentication), attractionId),
                HttpStatusCode.valueOf(200));
    }

    /**
     * 삭제 메서드
     */
    @Operation(summary = "명송 방문 기록 삭제", description = "유저가 명소에 방문한 기록을 삭제한다.")
    @DeleteMapping
    public ResponseEntity<?> deleteAchievement(Authentication authentication,
                                               @RequestParam(name = "attractionId") Long attractionId) {
        visitService.deleteAttractionAchievement(SecurityUtil.getCurrentName(authentication), attractionId);

        return ResponseEntity.ok(HttpStatusCode.valueOf(200));
    }


    /**
     * 조회 메서드
     */
    @Operation(summary = "지역별 명송 방문 기록 리스트", description = "지역별 유저가 명소에 방문한 기록을 가져온다.")
    @GetMapping("/list")
    public ResponseEntity<List<VisitResponse>> getList(Authentication authentication,
                                                       @RequestParam(name = "area") AreaCode area) {
        return new ResponseEntity<>(visitService.getList(SecurityUtil.getCurrentName(authentication), area), HttpStatusCode.valueOf(200));
    }

}
