package com.travelgo.backend.domain.report.service;

import com.travelgo.backend.domain.report.dto.ReportCountDto;
import com.travelgo.backend.domain.report.dto.ReportRequest;
import com.travelgo.backend.domain.report.dto.ReportResponse;
import com.travelgo.backend.domain.report.entity.Report;
import com.travelgo.backend.domain.report.model.constant.ReportCode;
import com.travelgo.backend.domain.report.repository.ReportRepository;
import com.travelgo.backend.domain.user.entity.User;
import com.travelgo.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final UserService userService;

    @Transactional
    public ReportResponse saveReport(ReportCode reportCode, ReportRequest reportRequest) {
        User reporter = userService.getUser(reportRequest.getReporterEmail());
        User reported = userService.getUserByNickname(reportRequest.getReportedNickname());

        Report report = createReport(reportCode, reportRequest, reporter, reported);

        Report saveReport = reportRepository.save(report);
        reported.report(); // report 카운트 증가

        return ReportResponse.of(saveReport);
    }

    public ReportCountDto getReportCount(String nickname) {
        User user = userService.getUserByNickname(nickname);

        return ReportCountDto.of(user);
    }

    private static Report createReport(ReportCode reportCode, ReportRequest reportRequest, User reporter, User reported) {
        return Report.builder()
                .reporter(reporter)
                .reported(reported)
                .reportCode(reportCode)
                .content(reportRequest.getContent())
                .build();
    }
}
