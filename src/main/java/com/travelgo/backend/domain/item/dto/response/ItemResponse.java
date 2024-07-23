package com.travelgo.backend.domain.item.dto.response;

import com.travelgo.backend.domain.area.entity.Area;
import com.travelgo.backend.domain.item.entity.Item;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {
    private Long itemId;
    private String itemName;
    private String imageUrl;
    private int itemRank;
    private Area area;
    private String summary;
    private String description;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteItem {
        private Long itemId;
        private String message;
    }

    public static ItemResponse fromEntity(Item item) {
        return new ItemResponse(
                item.getItemId(),
                item.getItemName(),
                item.getImageUrl(),
                item.getItemRank(),
                item.getArea(),
                item.getSummary(),
                item.getDescription()
        );
    }
}
