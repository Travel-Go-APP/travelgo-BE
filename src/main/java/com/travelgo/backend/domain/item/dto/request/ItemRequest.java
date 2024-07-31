package com.travelgo.backend.domain.item.dto.request;

import com.travelgo.backend.domain.area.entity.Area;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequest {

    @NotNull
    private Long itemId;

    @NotNull
    private String itemName;

    private String imageUrl;

    @NotNull
    private int itemRank;

    @NotNull
    private String area;

    @NotNull
    private String summary;

    @NotNull
    private String description;
}
