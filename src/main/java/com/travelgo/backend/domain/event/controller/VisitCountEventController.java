package com.travelgo.backend.domain.event.controller;

import com.travelgo.backend.domain.attraction.model.AreaCode;
import com.travelgo.backend.domain.event.dto.Period;
import com.travelgo.backend.domain.event.dto.VisitCountEventDto;
import com.travelgo.backend.domain.event.service.VisitCountEventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "방문자 수 이벤트", description = "방문자수 이벤트 API(#16)")
@RequestMapping("/api/event/visit-count")
public class VisitCountEventController {
    private final VisitCountEventService visitCountEventService;

    @Operation(summary = "지자체별 방문자수에 따른 이벤트 처리", description = "공공데이터 포털 방문자수 api를 받아서 이벤트를 실행한다.")
    @PostMapping
    public ResponseEntity<VisitCountEventDto> visitCountEvent(@RequestParam(name = "numOfRows", defaultValue = "10000") int numOfRows,
                                                              @RequestParam(name = "pageNo", defaultValue = "0") int pageNo,
                                                              @RequestParam(name = "email") String email,
                                                              @RequestParam(name = "area") AreaCode area,
                                                              @RequestBody Period period) throws ParseException {

        VisitCountEventDto visitCountEventDto = visitCountEventService.visitCountRewardEvent(numOfRows, pageNo, email, area, period);

        return new ResponseEntity<>(visitCountEventDto, HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "받았던 보상을 원래대로 되돌린다.", description = "유저의 증가량을 원래대로 되돌린다.")
    @PostMapping("/reset")
    public ResponseEntity<Double> resetReward(@RequestParam(name = "email") String email) {
        visitCountEventService.resetReward(email);

        return new ResponseEntity<>(null, HttpStatusCode.valueOf(200));
    }
}
