package com.travelgo.backend.domain.attraction.controller;

import com.travelgo.backend.domain.attraction.service.AttractionService;
import com.travelgo.backend.domain.attraction.service.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "명소 좋아요", description = "명소 개발 API(#40)")
@RequestMapping("/api/like")
public class LikesController {
    private final LikesService likesService;
    private final AttractionService attractionService;


    @Operation(summary = "명소 좋아요 누르기", description = "명소 좋아요")
    @PostMapping("/{attractionId}")
    public ResponseEntity<Void> pressLikes(@PathVariable("attractionId") Long attractionId, String email) {
        if (!likesService.duplicateLikes(attractionId, email)) {
            attractionService.pressLikes(attractionId);
            likesService.save(attractionId, email);
        } else {
            attractionService.cancelLikes(attractionId);
            likesService.delete(attractionId, email);
        }

        return new ResponseEntity<>(HttpStatusCode.valueOf(200));
    }
}
