package com.travelgo.backend.domain.report.dto;

import com.travelgo.backend.domain.user.entity.User;
import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportCountDto {
    private String nickname;

    private int reportCount;

    public static ReportCountDto of(User user) {
        return ReportCountDto.builder()
                .nickname(user.getNickname())
                .reportCount(user.getReportCount())
                .build();
    }
}
