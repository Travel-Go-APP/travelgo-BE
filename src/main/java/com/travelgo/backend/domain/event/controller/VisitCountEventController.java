package com.travelgo.backend.domain.event.controller;

import com.travelgo.backend.domain.attraction.entity.DataApiExplorer;
import com.travelgo.backend.domain.event.dto.VisitResult;
import com.travelgo.backend.domain.event.service.VisitCountEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequiredArgsConstructor
@Tag(name = "방문자 수 이벤트", description = "방문자수 이벤트 API(#16)")
@RequestMapping("/api/event/visit-count")
public class VisitCountEventController {
    private final VisitCountEventService visitCountEventService;

    @Operation(summary = "공공 데이터 포털 방문자 수 api 실행", description = "지자체별 방문자 수를 받는 api를 실행한다.")
    @GetMapping
    public ResponseEntity<String> loadCountApi(@RequestParam(name = "numOfRows", defaultValue = "10000") int numOfRows,
                                               @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                               @RequestParam(name = "month") int month) {
        // 현재 날짜와 시간
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        // 한 달 전 날짜 구하기
        calendar.add(Calendar.MONTH, -month);
        Date before = calendar.getTime();

        // 포맷팅 정의
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        int start = Integer.parseInt(formatter.format(before));
        int end = Integer.parseInt(formatter.format(now));

        String result = DataApiExplorer.getCountInfo(numOfRows, pageNo, start, end);

        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }


    @Operation(summary = "공공 데이터 포털 방문자 수 api 실행", description = "지자체별 방문자 수를 받는 api를 실행한다.")
    @PostMapping
    public ResponseEntity<VisitResult> countEvent(@RequestParam(name = "numOfRows", defaultValue = "10000") int numOfRows,
                                                  @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                  @RequestParam(name = "month") int month) {
        // 현재 날짜와 시간
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();

        // 한 달 전 날짜 구하기
        calendar.add(Calendar.MONTH, -month);
        Date before = calendar.getTime();

        // 포맷팅 정의
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

        int start = Integer.parseInt(formatter.format(before));
        int end = Integer.parseInt(formatter.format(now));

        String result = DataApiExplorer.getCountInfo(numOfRows, pageNo, start, end);
        VisitResult visitResult = visitCountEventService.visitCount(result);

        return new ResponseEntity<>(visitResult, HttpStatusCode.valueOf(200));
    }

}
