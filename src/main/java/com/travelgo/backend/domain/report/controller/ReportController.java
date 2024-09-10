package com.travelgo.backend.domain.report.controller;

import com.travelgo.backend.domain.report.dto.ReportCountDto;
import com.travelgo.backend.domain.report.dto.ReportRequest;
import com.travelgo.backend.domain.report.dto.ReportResponse;
import com.travelgo.backend.domain.report.model.constant.ReportCode;
import com.travelgo.backend.domain.report.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "신고", description = "신고 API(#44)")
@RequestMapping("/api/report")
public class ReportController {
    private final ReportService reportService;

    @Operation(summary = "신고하기", description = "유저가 신고하기")
    @PostMapping
    public ResponseEntity<ReportResponse> Report(@RequestParam(name = "reportCode") ReportCode reportCode,
                                                 @RequestBody ReportRequest reportRequest) {
        return new ResponseEntity<>(reportService.saveReport(reportCode, reportRequest), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "닉네임을 통해 신고 누적 수 보기", description = "유저의 신고 누적 수 보기")
    @GetMapping("/count")
    public ResponseEntity<ReportCountDto> ReportCounting(@RequestParam(name = "nickname") String nickname) {
        return new ResponseEntity<>(reportService.getReportCount(nickname), HttpStatusCode.valueOf(200));
    }
}
