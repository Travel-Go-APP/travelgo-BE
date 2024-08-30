package com.travelgo.backend.domain.event.entity;

import com.travelgo.backend.domain.area.entity.Area;
import com.travelgo.backend.domain.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "event_name")
    private String eventName;

    private long periodDays;
    private LocalDate startDate;
    private LocalDate endDate;

    private String description;

    @Enumerated(EnumType.STRING)
    private Area area;

    @Builder
    public Event(String eventName, LocalDate startDate, LocalDate endDate, String description, Area area) {
        this.eventName = eventName;
        this.periodDays = ChronoUnit.DAYS.between(startDate,endDate);
        this.description = description;
        this.area = area;
    }
}
