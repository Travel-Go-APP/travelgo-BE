package com.travelgo.backend.domain.user.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Bag {
    초급("초급", 1),
    중급("중급", 2),
    상급("고급",3 );

    @JsonValue
    private final String type;
    private final int level;
}
