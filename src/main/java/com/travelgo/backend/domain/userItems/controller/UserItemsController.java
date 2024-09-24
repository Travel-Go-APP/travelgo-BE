package com.travelgo.backend.domain.userItems.controller;

import com.travelgo.backend.domain.userItems.dto.response.UserItemsResponse;
import com.travelgo.backend.domain.userItems.service.UserItemsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저&아이템", description = "유저 획득 아이템 API")
@RequestMapping("/api/user-items")
public class UserItemsController {

    @Autowired
    UserItemsService userItemsService;

    @Operation(summary = "아이템 획득", description = "유저가 아이템을 획득한다")
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addUserItems(@RequestParam(name = "email") String email,
                                                            @RequestParam(name = "latitude") Double latitude,
                                                            @RequestParam(name = "longitude") Double longitude){
        Map<String,Object> response = userItemsService.add(email, latitude, longitude);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "아이템 리스트 조회", description = "유저 아이템 획득 리스트 조회")
    @GetMapping("/items")
    public ResponseEntity<Map<Long, Integer>> getItemIdsByUserEmail(@RequestParam(name = "email") String email){
        Map<Long, Integer> itemIds = userItemsService.getItemIdsByUserEmail(email);
        return ResponseEntity.ok(itemIds);
    }

    @Operation(summary = "유저 아이템 정보 조회", description = "유저의 아이템 정보와 지역별 수집 현황 조회")
    @GetMapping("/user-info")
    public ResponseEntity<UserItemsResponse> getUserItemsResponse(@RequestParam(name = "email") String email) {
        UserItemsResponse response = userItemsService.getUserItemsResponse(email);
        return ResponseEntity.ok(response);
    }

}
