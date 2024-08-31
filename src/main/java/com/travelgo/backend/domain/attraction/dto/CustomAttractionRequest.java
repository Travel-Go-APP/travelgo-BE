package com.travelgo.backend.domain.attraction.dto;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.attraction.model.BigCategory;
import com.travelgo.backend.domain.attraction.model.MiddleCategory;
import com.travelgo.backend.domain.attraction.model.SmallCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomAttractionRequest {
    @NotNull
    @Schema(description = "명소 이름")
    private String attractionName;

    @NotNull
    @Schema(description = "주소")
    private String address; //주소

    @NotNull
    @Schema(description = "설명")
    private String description; //설명

    @Enumerated(EnumType.STRING)
    private AreaCode area;

    @Enumerated(EnumType.STRING)
    private BigCategory bigCategory;

    @Enumerated(EnumType.STRING)
    private MiddleCategory middleCategory;

    @Enumerated(EnumType.STRING)
    private SmallCategory smallCategory;
}
