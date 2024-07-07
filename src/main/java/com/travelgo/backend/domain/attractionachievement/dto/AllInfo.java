package com.travelgo.backend.domain.attractionachievement.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class AllInfo {
    private Long totalCount;

    private Long visitCount;

    @Builder
    public AllInfo(Long totalCount, Long visitCount) {
        this.totalCount = totalCount;
        this.visitCount = visitCount;
    }
}
