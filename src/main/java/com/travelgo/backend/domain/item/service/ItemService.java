package com.travelgo.backend.domain.item.service;

import com.travelgo.backend.domain.area.entity.Area;
import com.travelgo.backend.domain.item.dto.request.ItemRequest;
import com.travelgo.backend.domain.item.dto.response.ItemResponse;
import com.travelgo.backend.domain.item.entity.Item;
import com.travelgo.backend.domain.item.repository.ItemRepository;
import com.travelgo.backend.domain.user.dto.Response.UserResponse;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void addItem(Long itemId, String itemName, String imageUrl, String itemRank, Area area, String summary, String description) {
        Optional<Item> existingItem = itemRepository.findByItemId(itemId);

        if(existingItem.isPresent()){
            throw new CustomException(ErrorCode.BAD_REQUEST);
        }

        Item newItem = Item.createItem(itemId, itemName, imageUrl, itemRank, area, summary, description);

        itemRepository.save(newItem);
    }

    @Transactional
    public ItemResponse.DeleteItem deleteItem(Long itemId){
        Item item = itemRepository.findByItemId(itemId).orElseThrow(() -> new CustomException(ErrorCode.BAD_REQUEST));
        itemRepository.delete(item);

        return new ItemResponse.DeleteItem(itemId, "아이템이 삭제 되었습니다.");
    }
}
