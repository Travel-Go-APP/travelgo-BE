package com.travelgo.backend.domain.item.controller;

import com.travelgo.backend.domain.item.dto.request.ItemRequest;
import com.travelgo.backend.domain.item.service.ItemService;
import com.travelgo.backend.domain.user.dto.Response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "아이템", description = "아이템 API")
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "아이템 추가", description = "아이템 추가 및 기본 값 설정")
    @PostMapping("/add")
    public ResponseEntity<UserResponse> addItem(@Valid @RequestBody ItemRequest request){
        itemService.addItem(request.getItemId(), request.getItemName(), request.getImageUrl(), request.getItemRank(),
                request.getArea(), request.getSummary(), request.getDescription());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
