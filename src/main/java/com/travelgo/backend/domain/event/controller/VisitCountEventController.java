package com.travelgo.backend.domain.event.controller;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.event.dto.Period;
import com.travelgo.backend.domain.event.dto.VisitCountEventDto;
import com.travelgo.backend.domain.event.entity.VisitCountBenefit;
import com.travelgo.backend.domain.event.service.VisitCountEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "방문자 수 이벤트", description = "방문자수 이벤트 API(#16)")
@RequestMapping("/api/event/visit-count")
public class VisitCountEventController {
    private static final int NUM_OF_ROWS = 10000;
    private static final int PAGE_NO = 0;

    private final VisitCountEventService visitCountEventService;

    @Operation(summary = "지자체별 방문자수 증가에 따른 이벤트 적용", description = "공공데이터 포털 방문자수 api를 받아온 데이터로 이벤트 적용한다.")
    @PostMapping
    public ResponseEntity<VisitCountEventDto> getBenefit(@RequestParam(name = "email") String email,
                                                         @RequestParam(name = "area") AreaCode area) {
        VisitCountEventDto benefit = visitCountEventService.getBenefit(email, area);
        return new ResponseEntity<>(benefit, HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "지자체별 방문자수 테이블 초기화", description = "공공데이터 포털 방문자수 api를 받아온 데이터로 테이블을 초기화한다.")
    @PostMapping("/initial")
    public ResponseEntity<Void> initialVisitCountTable() throws ParseException {
        List<VisitCountBenefit> visitCountData = visitCountEventService.getVisitCountData(NUM_OF_ROWS, PAGE_NO, new Period());
        visitCountEventService.initialVisitCount(visitCountData);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(200));
    }

}
