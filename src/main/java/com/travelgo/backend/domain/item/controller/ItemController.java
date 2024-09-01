package com.travelgo.backend.domain.item.controller;

import com.travelgo.backend.domain.attraction.dto.AttractionRequest;
import com.travelgo.backend.domain.item.dto.request.ItemRequest;
import com.travelgo.backend.domain.item.dto.response.ItemResponse;
import com.travelgo.backend.domain.item.dto.response.ShopResponse;
import com.travelgo.backend.domain.item.entity.Item;
import com.travelgo.backend.domain.item.service.ItemService;
import com.travelgo.backend.domain.user.dto.Response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Tag(name = "아이템", description = "아이템 API")
@RequestMapping("/api/item")
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "아이템 추가", description = "아이템 추가 및 기본 값 설정")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> addItem(@Valid @RequestPart(value = "ItemRequest") ItemRequest request,
                                        @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        itemService.addItem(request, image);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "위치기반 아이템 획득", description = "사용자 위경도로 지역 추출 후 아이템 랜덤 획득")
    @PostMapping("/acquire")
    public ResponseEntity<ItemResponse> acquireItem(@RequestParam(name = "email") String email,
                                                    @RequestParam(name = "latitude") Double latitude,
                                                    @RequestParam(name = "longitude") Double longitude) {
        ItemResponse itemResponse = itemService.acquireItem(email, latitude, longitude);
        return new ResponseEntity<>(itemResponse, HttpStatus.OK);
    }

    @Operation(summary = "아이템 삭제", description = "아이템 삭제")
    @DeleteMapping("/{itemId}")
    public ResponseEntity<ItemResponse.DeleteItem> deleteItem(@PathVariable(name = "itemId") Long itemId){
        ItemResponse.DeleteItem response = itemService.deleteItem(itemId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(200));
    }

    @Operation(summary = "아이템 업데이트", description = "아이템 이름, 랭크 업데이트")
    @PutMapping("/update")
    public ResponseEntity<Void> updateItem(@RequestParam(name = "item_id") Long itemId,
                                           @RequestParam(name = "item_name") String itemName,
                                           @RequestParam(name = "item_rank") int itemRank){
        itemService.updateItem(itemId, itemName, itemRank);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "개별 아이템 조회", description = "아이템 정보 조회")
    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponse> getItem(@RequestParam(name = "item_id") Long itemId){
        Item item = itemService.getItem(itemId);
        ItemResponse itemResponse = ItemResponse.fromEntity(item);
        return new ResponseEntity<>(itemResponse,HttpStatus.OK);
    }

    @Operation(summary = "상점 뽑기", description = "상점에서 등급별 뽑기 구매")
    @GetMapping("/shop")
    public ResponseEntity<ShopResponse> buyShop(@RequestParam(name = "email") String email,
                                                @RequestParam(name = "gachaLevel") Integer gachaLevel){
        ShopResponse response = itemService.buyShop(email, gachaLevel);
        return ResponseEntity.ok(response);
    }
}
