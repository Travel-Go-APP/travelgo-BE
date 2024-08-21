package com.travelgo.backend.domain.event.dto;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import lombok.*;

@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitResult {
    AreaCode maxArea;
    AreaCode minArea;
}
