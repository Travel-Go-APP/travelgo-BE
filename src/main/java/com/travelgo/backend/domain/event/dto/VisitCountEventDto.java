package com.travelgo.backend.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VisitCountEventDto {
    private int ranking;
    private String type;
    private Double ratio;
}
