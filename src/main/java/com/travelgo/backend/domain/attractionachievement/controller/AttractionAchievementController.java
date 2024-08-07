package com.travelgo.backend.domain.attractionachievement.controller;

import com.travelgo.backend.auth.dto.model.PrincipalDetails;
import com.travelgo.backend.auth.utils.SecurityUtil;
import com.travelgo.backend.domain.attractionachievement.service.AttractionAchievementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Map<String, Object>> getAllInfo(Authentication authentication) {
        return new ResponseEntity<>(attractionAchievementService.getAttractionAchievement(SecurityUtil.getCurrentName(authentication)),
                HttpStatusCode.valueOf(200));
    }

}
