package com.travelgo.backend.domain.attractionachievement.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VisitStatus {
    visit("방문"),
    notvisit("미방문"),
    ;

    @JsonValue
    private final String status;
}
