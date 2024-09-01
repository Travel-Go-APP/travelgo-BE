package com.travelgo.backend.domain.report.entity;

import com.travelgo.backend.domain.attraction.entity.Attraction;
import com.travelgo.backend.domain.report.model.constant.ReportCode;
import com.travelgo.backend.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_id")
    private User reported;

    @Enumerated(EnumType.STRING)
    private ReportCode reportCode;

    private String content;

    @Builder
    public Report(User reporter, User reported, ReportCode reportCode, String content) {
        this.reporter = reporter;
        this.reported = reported;
        this.reportCode = reportCode;
        this.content = content;
    }
}
