package com.travelgo.backend.domain.event.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class Period {
    private int startDate;
    private int middleDate;
    private int endDate;

    public Period() {
        LocalDate today = LocalDate.now();
        this.endDate = convertToInt(today); // 오늘
        this.middleDate = convertToInt(today.minusMonths(1)); // 한 달 전
        this.startDate = convertToInt(today.minusMonths(2)); // 두 달 전
    }

    private int convertToInt(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return Integer.parseInt(date.format(formatter));
    }
}
