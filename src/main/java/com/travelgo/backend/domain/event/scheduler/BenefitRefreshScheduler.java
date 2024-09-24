package com.travelgo.backend.domain.event.scheduler;

import com.travelgo.backend.domain.event.dto.Period;
import com.travelgo.backend.domain.event.entity.VisitCountBenefit;
import com.travelgo.backend.domain.event.repository.BenefitBulkRepository;
import com.travelgo.backend.domain.event.repository.VisitCountBenefitRepository;
import com.travelgo.backend.domain.event.service.VisitCountEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class BenefitRefreshScheduler {
    private static final int NUM_OF_ROWS = 10000;
    private static final int PAGE_NO = 0;

    private final VisitCountEventService visitCountEventService;
    private final VisitCountBenefitRepository visitCountBenefitRepository;
    private final BenefitBulkRepository benefitBulkRepository;

    //    @Scheduled(cron = "0 0 0 */14 * ?") // 2주마다 자정에 실행
//    @Scheduled(cron = "*/10 * * * * ?") // 매 10초마다 실행
    @Scheduled(cron = "0 0 0 1 * ?", zone = "Asia/Seoul") // 매달 1일 자정에 실행
    public void updateBenefit() throws ParseException {
        System.out.println("Benefit 동작: ");
        // 새로운 데이터를 가져오는 로직
        List<VisitCountBenefit> benefit = visitCountEventService.getVisitCountData(NUM_OF_ROWS, PAGE_NO, new Period());

        // 기존 데이터를 삭제하고 새로운 데이터 저장
        visitCountBenefitRepository.deleteAll();
        benefitBulkRepository.saveAll(benefit);

        log.info("데이터가 갱신되었습니다.");
    }

}
