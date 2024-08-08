package com.travelgo.backend.domain.item.service;

import com.travelgo.backend.domain.area.entity.Area;
import com.travelgo.backend.domain.attractionImage.service.S3UploadService;
import com.travelgo.backend.domain.item.dto.request.ItemRequest;
import com.travelgo.backend.domain.item.dto.response.ItemResponse;
import com.travelgo.backend.domain.item.entity.Item;
import com.travelgo.backend.domain.item.repository.ItemRepository;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final S3UploadService s3UploadService;

    @Transactional
    public void addItem(ItemRequest request, MultipartFile imageFile) throws IOException {
        // 아이템이 이미 존재하는지 확인
        Optional<Item> existingItem = itemRepository.findByItemId(request.getItemId());

        if (existingItem.isPresent()) {
            throw new CustomException(ErrorCode.NOT_FOUND_ITEM); // 아이템이 이미 존재하는 경우
        }

        // 이미지 URL 처리
        String imageUrl = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageUrl = s3UploadService.upload(imageFile, "items");
        }

        // 문자열을 Area Enum으로 변환
        Area area;
        try {
            area = Arrays.stream(Area.values())
                    .filter(a -> a.getValue().equalsIgnoreCase(request.getArea()))
                    .findFirst()
                    .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_AREA)); // 유효하지 않은 Area 값 처리
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.NOT_FOUND_AREA); // 유효하지 않은 Area 값 처리
        }

        // 새로운 Item 객체 생성
        Item newItem = Item.createItem(request.getItemName(), imageUrl, request.getItemRank(),
                area, request.getSummary(), request.getDescription());

        // 아이템 저장
        itemRepository.save(newItem);
    }



    @Transactional
    public void updateItem(Long itemId, String itemName, int itemRank){
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));

        item.updateItemName(itemName);
        item.updateItemRank(itemRank);

        itemRepository.save(item);
    }

    @Transactional
    public ItemResponse.DeleteItem deleteItem(Long itemId){
        Item item = itemRepository.findByItemId(itemId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        itemRepository.delete(item);

        return new ItemResponse.DeleteItem(itemId, "아이템이 삭제 되었습니다.");
    }

    @Transactional(readOnly = true)
    public Item getItem(Long itemId) {
        return itemRepository.findByItemId(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
    }
}
