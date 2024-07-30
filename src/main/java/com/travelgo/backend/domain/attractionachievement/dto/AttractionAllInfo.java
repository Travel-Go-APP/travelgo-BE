package com.travelgo.backend.domain.attractionachievement.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class AttractionAllInfo {
    private Long totalCount;

    private Long visitCount;

    @Builder
    public AttractionAllInfo(Long totalCount, Long visitCount) {
        this.totalCount = totalCount;
        this.visitCount = visitCount;
    }
}
