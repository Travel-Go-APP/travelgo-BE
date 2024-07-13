package com.travelgo.backend.domain.item.dto.response;

import com.travelgo.backend.domain.area.entity.Area;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {
    private Long itemId;
    private String itemName;
    private String imageUrl;
    private String itemRank;
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
}
