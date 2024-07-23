package com.travelgo.backend.domain.userItems.dto.response;

import com.travelgo.backend.domain.item.entity.Item;
import com.travelgo.backend.domain.user.entity.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserItemsResponse {
    private Long Id;
    private User user;
    private Item item;

    @Getter
    @AllArgsConstructor
    public static class User{
        Long userId;
    }
    @Getter
    @AllArgsConstructor
    public static class Item{
        Long itemId;
    }
}
