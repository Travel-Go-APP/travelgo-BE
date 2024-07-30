package com.travelgo.backend.domain.attractionachievement.dto;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import lombok.Builder;
import lombok.Data;

@Data
public class AttractionInfo {
    private AreaCode area;

    private Long totalCount;

    private Long visitCount;

    @Builder
    public AttractionInfo(AreaCode area, Long totalCount, Long visitCount) {
        this.area = area;
        this.totalCount = totalCount;
        this.visitCount = visitCount;
    }
}
