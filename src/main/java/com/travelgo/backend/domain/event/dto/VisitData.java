package com.travelgo.backend.domain.event.dto;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VisitData {
    private AreaCode areaCode;
    private Double visitorCount;

    public void addCount(Double Num) {
        this.visitorCount += Num;
    }
}
