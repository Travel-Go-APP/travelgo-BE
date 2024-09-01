package com.travelgo.backend.domain.report.dto;

import com.travelgo.backend.domain.report.entity.Report;
import com.travelgo.backend.domain.report.model.constant.ReportCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

    private String reporter;

    private String reported;

    private ReportCode reportCode;

    private String content;

    public static ReportResponse of(Report report) {
        return ReportResponse.builder()
                .reporter(report.getReporter().getNickname())
                .reported(report.getReported().getNickname())
                .reportCode(report.getReportCode())
                .content(report.getContent())
                .build();
    }
}
