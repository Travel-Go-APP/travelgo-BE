package com.travelgo.backend.domain.item.service;

import com.travelgo.backend.domain.area.entity.Area;
import com.travelgo.backend.domain.item.dto.request.ItemRequest;
import com.travelgo.backend.domain.item.entity.Item;
import com.travelgo.backend.domain.item.repository.ItemRepository;
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
}
