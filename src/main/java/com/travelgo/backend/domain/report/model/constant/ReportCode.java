package com.travelgo.backend.domain.report.model.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportCode {

    USING_BAD_WORD("비속어를 사용했습니다."),
    USING_BAD_CONTENT("부적절한 내용이 포함되었습니다."),
    USING_MACRO("매크로를 사용했습니다."),

    FALSE_INFORMATION("허위 정보가 포함되어 있습니다."),

    OTHER("기타 신고 사유입니다.");

    private final String message;
}
