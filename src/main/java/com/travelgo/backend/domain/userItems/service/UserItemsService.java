package com.travelgo.backend.domain.userItems.service;

import com.travelgo.backend.domain.item.entity.Item;
import com.travelgo.backend.domain.item.repository.ItemRepository;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.repository.UserRepository;
import com.travelgo.backend.domain.userItems.entity.UserItems;
import com.travelgo.backend.domain.userItems.repository.UserItemsRepository;
import com.travelgo.backend.global.exception.CustomException;
import com.travelgo.backend.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserItemsService {

    private final UserItemsRepository userItemsRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public void add(Long userId, Long itemId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER));
        Item item = itemRepository.findByItemId(itemId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ITEM));

        UserItems userItems = UserItems.builder()
                .user(user)
                .item(item)
                .build();

        userItemsRepository.save(userItems);
    }

    public List<Long> getItemIdsByUserId(Long userId){
        List<UserItems> userItemsList = userItemsRepository.findAllByUser_UserId(userId);

        return userItemsList.stream()
                .map(userItems -> userItems.getItem().getItemId())
                .collect(Collectors.toList());
    }


}
