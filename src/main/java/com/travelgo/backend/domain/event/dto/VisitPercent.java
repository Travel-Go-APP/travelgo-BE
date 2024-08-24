package com.travelgo.backend.domain.event.dto;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitPercent {
    AreaCode areaCode;
    Double percent;
}
