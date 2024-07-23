package com.travelgo.backend.domain.userItems.controller;

import com.travelgo.backend.domain.userItems.dto.response.UserItemsResponse;
import com.travelgo.backend.domain.userItems.service.UserItemsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "유저&아이템", description = "유저 획득 아이템 API")
@RequestMapping("/api/userItems")
public class UserItemsController {

    @Autowired
    UserItemsService userItemsService;

    @Operation(summary = "아이템 획득", description = "유저가 아이템을 획득한다")
    @PostMapping("/add")
    public ResponseEntity<UserItemsResponse> addUserItems(@RequestParam(name = "userId") Long userId,
                                                          @RequestParam(name = "itemId") Long itemId){
        userItemsService.add(userId, itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "아이템 리스트 조회", description = "유저 아이템 획득 리스트 조회")
    @GetMapping("/items")
    public ResponseEntity<List<Long>> getItemIdsByUserId(@RequestParam(name = "userId") Long userId){
        List<Long> itemIds = userItemsService.getItemIdsByUserId(userId);
        return ResponseEntity.ok(itemIds);
    }

}
