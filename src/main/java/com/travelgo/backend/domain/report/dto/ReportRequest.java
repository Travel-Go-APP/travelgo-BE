package com.travelgo.backend.domain.report.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportRequest {

    @NotNull
    private String reporterEmail;

    @NotNull
    private String reportedNickname;

    private String content;
}
